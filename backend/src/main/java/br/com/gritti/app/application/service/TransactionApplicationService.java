package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.installment.InstallmentCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.InstallmentMapper;
import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.model.*;
import br.com.gritti.app.domain.service.*;
import br.com.gritti.app.shared.exceptions.InvalidPaymentTypeException;
import br.com.gritti.app.shared.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionApplicationService {

  private final TransactionDomainService transactionDomainService;
  private final TransactionMapper transactionMapper;
  private final InstallmentMapper installmentMapper;
  private final InvoiceDomainService invoiceDomainService;
  private final BankAccountDomainService bankAccountDomainService;
  private final CardDomainService cardDomainService;
  private final PagedResourcesAssembler<TransactionResponseDTO> assembler;
  private final InstallmentDomainService installmentDomainService;
  private final Logger log = LoggerFactory.getLogger(TransactionApplicationService.class);

  @Autowired
  public TransactionApplicationService(TransactionDomainService transactionDomainService, TransactionMapper transactionMapper,
                                       PagedResourcesAssembler<TransactionResponseDTO> assembler, InstallmentDomainService installmentDomainService,
                                       InstallmentMapper installmentMapper, InvoiceDomainService invoiceDomainService, CardDomainService cardDomainService,
                                       BankAccountDomainService bankAccountDomainService) {
    this.installmentDomainService = installmentDomainService;
    this.transactionDomainService = transactionDomainService;
    this.bankAccountDomainService = bankAccountDomainService;
    this.cardDomainService = cardDomainService;
    this.invoiceDomainService = invoiceDomainService;
    this.installmentMapper = installmentMapper;
    this.transactionMapper = transactionMapper;
    this.assembler = assembler;
  }

  public PagedModel<EntityModel<TransactionResponseDTO>> getTransactions(Pageable pageable, String username) {
    log.info("APPLICATION: Request received from controller and passing to domain to get all transactions");
    String currentUsername = SecurityUtil.getCurrentUsername();
    boolean isAdmin = SecurityUtil.isAdmin();
    Page<Transaction> transactions;
    if(username != null && !username.isBlank()) {
      if(!isAdmin && !username.equals(currentUsername)){
        throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
      }
      transactions = transactionDomainService.getTransactions(pageable, username);
    } else if(!isAdmin){
      username = currentUsername;
      transactions = transactionDomainService.getTransactions(pageable, username);
    } else {
      transactions = transactionDomainService.getTransactions(pageable);
    }

    Page<TransactionResponseDTO> transactionsWithLinks = transactions.map(transactionMapper::transactionToTransactionResponseDTO);
    return assembler.toModel(transactionsWithLinks);
  }

  public TransactionResponseDTO getTransactionById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to domain to get a transaction by id: {}", id);
    Transaction transaction = transactionDomainService.getTransactionById(id);
    return transactionMapper.transactionToTransactionResponseDTO(transaction);
  }

  @Transactional
  public Transaction createTransaction(TransactionCreateDTO transactionCreateDTO, UUID cardId) {
    log.info("APPLICATION: Request received from controller and passing to domain to create a new transaction");
    Transaction transaction = transactionMapper.transactionCreateDTOtoTransaction(transactionCreateDTO);
    try {
      switch(transaction.getPaymentType()) {
        case CREDIT:
            if(transactionCreateDTO.getInstallmentValue() != null && transactionCreateDTO.getNumberInstallment() != null) {
              processInstallmentTransaction(transaction, transactionCreateDTO, cardId);
            }
            processCreditTransaction(transaction, cardId);
          break;

        case DEBIT:
            processDebitTransaction(transaction, cardId);
          break;

        case TRANSFER:
          processTransferTransaction(transactionCreateDTO);
          break;

        default:
          throw new InvalidPaymentTypeException("Invalid payment type: " + transaction.getPaymentType());
      }
    } catch (BadRequestException e) {
      throw new RuntimeException(e);
    }
    return transactionDomainService.createTransaction(transaction);
  }

  private void processInstallmentTransaction(Transaction transaction, TransactionCreateDTO transactionCreateDTO, UUID cardId) {
    Date dueDate = DateUtils.addMonths(new Date(), transactionCreateDTO.getNumberInstallment());
    InstallmentCreateDTO installmentCreateDTO = new InstallmentCreateDTO(transactionCreateDTO.getNumberInstallment(), transactionCreateDTO.getInstallmentValue(), dueDate);
    Card card = cardDomainService.getCardById(cardId);

    Installment installment = installmentDomainService.createInstallment(installmentMapper.installmentCreateDTOtoInstallment(installmentCreateDTO));
    transaction.setInstallment(installment);

    Calendar cal = Calendar.getInstance();
    cal.setTime(transaction.getTimestamp());

    for (int i = 0; i < installment.getNumberInstallment(); i++) {
      if (i > 0) {
        cal.add(Calendar.MONTH, 1);
      }

      Invoice invoiceForInstallment = invoiceDomainService.getOrCreateInvoiceForDate(cal.getTime(), card);

      invoiceForInstallment.getInstallment().add(installment);

      Invoice firstInvoice = invoiceDomainService.getOrCreateInvoiceForDate(transaction.getTimestamp(), card);
      transaction.setInvoice(firstInvoice);
    }
  }

  private void processCreditTransaction(Transaction transaction, UUID cardId) {
    Card card = cardDomainService.getCardById(cardId);
    Invoice invoice = invoiceDomainService.getOrCreateInvoiceForDate(transaction.getTimestamp(), card);

    transaction.setInvoice(invoice);
  }

  private void processDebitTransaction(Transaction transaction, UUID cardId) throws BadRequestException {
    Card card = cardDomainService.getCardById(cardId);
    BankAccount account = card.getBankAccount();
    if(transaction.getValue() > account.getBalance()) throw new BadRequestException("Insufficient balance");
    account.setBalance(account.getBalance() - transaction.getValue());
    card.setBankAccount(account);
    cardDomainService.updateCard(card.getId(), card);
    transactionDomainService.createTransaction(transaction);
  }

  private void processTransferTransaction(TransactionCreateDTO transactionCreateDTO) throws BadRequestException {
    BankAccount sourceAccount = bankAccountDomainService.getAccountById(transactionCreateDTO.getFromAccountId());
    BankAccount targetAccount = bankAccountDomainService.getAccountById(transactionCreateDTO.getToAccountId());
    if(transactionCreateDTO.getValue() > sourceAccount.getBalance()) throw new BadRequestException("Insufficient balance from account: " + sourceAccount.getId());
    sourceAccount.setBalance(sourceAccount.getBalance() - transactionCreateDTO.getValue());
    targetAccount.setBalance(targetAccount.getBalance() + transactionCreateDTO.getValue());
    bankAccountDomainService.updateAccount(sourceAccount.getId(), sourceAccount);
    bankAccountDomainService.updateAccount(targetAccount.getId(), targetAccount);
    Transaction transaction = transactionMapper.transactionCreateDTOtoTransaction(transactionCreateDTO);
    transaction.setTimestamp(new Date());
    transactionDomainService.createTransaction(transaction);
  }

  public void deleteTransaction(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to domain to delete a transaction by id: {}", id);
    transactionDomainService.deleteTransaction(id);
  }
}
