package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.*;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.infra.repository.TransactionRepositoryImpl;
import br.com.gritti.app.shared.exceptions.InvalidPaymentTypeException;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionDomainService {
  private final Logger log = LoggerFactory.getLogger(TransactionDomainService.class);

  private final CardDomainService cardDomainService;
  private final InvoiceDomainService invoiceDomainService;
  private final InstallmentDomainService installmentDomainService;
  private final BankAccountDomainService bankAccountDomainService;
  private final TransactionRepositoryImpl transactionRepositoryImpl;

  @Autowired
  public TransactionDomainService(CardDomainService cardDomainService, InvoiceDomainService invoiceDomainService,
                                  InstallmentDomainService installmentDomainService, TransactionRepositoryImpl transactionRepositoryImpl,
                                  BankAccountDomainService bankAccountDomainService) {
    this.cardDomainService = cardDomainService;
    this.invoiceDomainService = invoiceDomainService;
    this.installmentDomainService = installmentDomainService;
    this.bankAccountDomainService = bankAccountDomainService;
    this.transactionRepositoryImpl = transactionRepositoryImpl;
  }

  public Page<Transaction> getTransactions(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all transactions from the repository");
    return transactionRepositoryImpl.findAll(pageable);
  }

  public Page<Transaction> getTransactions(Pageable pageable, String username) {
    log.info("DOMAIN: Request received from application and getting all transactions from the repository and filtering by username: {}", username);
    return transactionRepositoryImpl.findAllByUsername(pageable, username);
  }

  public Transaction getTransactionById(UUID id) {
    log.info("DOMAIN: Request received from application and getting transaction with id {} from the repository", id);
    return transactionRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id " + id + " not found"));
  }

  public Transaction createTransaction(Transaction transaction) {
    log.info("DOMAIN: Request received from application and saving a new transaction from the repository");
    transactionRepositoryImpl.save(transaction);
    return transaction;
  }

  public void deleteTransaction(UUID id) {
    log.info("DOMAIN: Request received from application and deleting transaction with id {}", id);
    Transaction transaction = transactionRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id " + id + " not found"));
    transactionRepositoryImpl.delete(transaction);
  }

  @Transactional
  public Transaction processTransaction(Transaction transaction,
                                        TransactionProcessingData transactionProcessingData) throws BadRequestException {
    log.info("DOMAIN: Processing transaction with payment type: {}", transaction.getPaymentType());
    switch (transaction.getPaymentType()) {
      case CREDIT:
        if(transactionProcessingData.getCardId() == null){
          throw new IllegalArgumentException("Card id is required for credit transactions");
        }
        if((transactionProcessingData.getInstallmentData() == null ||
                (transactionProcessingData.getInstallmentData().getInstallmentValue() == null &&
                        transactionProcessingData.getInstallmentData().getNumberInstallment() == null))
        ){
          processCreditTransaction(transaction, transactionProcessingData.getCardId());
        } else {
          processInstallmentTransaction(transaction, transactionProcessingData);
        }
        break;
      case DEBIT:
        if(transactionProcessingData.getCardId() == null){
          throw new IllegalArgumentException("Card id is required for debit transactions");
        }
        processDebitTransaction(transaction, transactionProcessingData.getCardId());
        break;
      case TRANSFER:
        if(transactionProcessingData.getFromAccountId() == null || transactionProcessingData.getToAccountId() == null){
          throw new IllegalArgumentException("From account id and to account id are required for transfer transactions");
        }
        processTransferTransaction(transaction, transactionProcessingData.getFromAccountId(), transactionProcessingData.getToAccountId());
        break;
      default:
        throw new InvalidPaymentTypeException("Invalid payment type: " + transaction.getPaymentType());
    }
    return createTransaction(transaction);
  }

  private void processInstallmentTransaction(Transaction transaction, TransactionProcessingData transactionProcessingData) throws BadRequestException {
    Card card = cardDomainService.getCardById(transactionProcessingData.getCardId());

    Installment installment = installmentDomainService.createInstallment(transactionProcessingData.getInstallmentData());
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
  }

  private void processTransferTransaction(Transaction transaction, UUID fromAccountId,UUID toAccountId) throws BadRequestException {
    BankAccount sourceAccount = bankAccountDomainService.getAccountById(fromAccountId);
    BankAccount targetAccount = bankAccountDomainService.getAccountById(toAccountId);

    if(transaction.getValue() > sourceAccount.getBalance()) throw new BadRequestException("Insufficient balance from account: " + sourceAccount.getId());

    sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getValue());
    targetAccount.setBalance(targetAccount.getBalance() + transaction.getValue());

    bankAccountDomainService.updateAccount(sourceAccount.getId(), sourceAccount);
    bankAccountDomainService.updateAccount(targetAccount.getId(), targetAccount);

    transaction.setTimestamp(new Date());
  }

}
