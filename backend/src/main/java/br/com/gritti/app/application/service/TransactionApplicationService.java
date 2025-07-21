package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.model.*;
import br.com.gritti.app.domain.service.*;
import br.com.gritti.app.domain.valueobject.InstallmentData;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.shared.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionApplicationService {

  private final TransactionDomainService transactionDomainService;
  private final InvoiceDomainService invoiceDomainService;
  private final TransactionMapper transactionMapper;
  private final PagedResourcesAssembler<TransactionResponseDTO> assembler;
  private final Logger log = LoggerFactory.getLogger(TransactionApplicationService.class);

  @Autowired
  public TransactionApplicationService(TransactionDomainService transactionDomainService, TransactionMapper transactionMapper,
                                       PagedResourcesAssembler<TransactionResponseDTO> assembler, InvoiceDomainService invoiceDomainService) {
    this.transactionDomainService = transactionDomainService;
    this.invoiceDomainService = invoiceDomainService;
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

  public PagedModel<EntityModel<TransactionResponseDTO>> getInvoiceTransactions(Pageable pageable, UUID invoiceId) {
    log.info("APPLICATION: Request received from controller and passing to domain to get the {} transactions", invoiceId);
    Invoice invoice = invoiceDomainService.getInvoiceById(invoiceId);
    Page<Transaction> transactions = transactionDomainService.getInvoiceTransactions(pageable, invoice);
    Page<TransactionResponseDTO> transactionsWithLinks = transactions.map(transactionMapper::transactionToTransactionResponseDTO);
    return assembler.toModel(transactionsWithLinks);
  }

  public TransactionResponseDTO getTransactionById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to domain to get a transaction by id: {}", id);
    Transaction transaction = transactionDomainService.getTransactionById(id);
    return transactionMapper.transactionToTransactionResponseDTO(transaction);
  }

  @Transactional
  public List<TransactionResponseDTO> createTransaction(TransactionCreateDTO transactionCreateDTO, UUID cardId) throws BadRequestException {
    log.info("APPLICATION: Request received from controller and passing to domain to create a new transaction");
    if(transactionCreateDTO.getPaymentType().equals(PaymentType.TRANSFER)) throw new BadRequestException("Wrong endpoint to create a transfer transaction type");
    TransactionProcessingData processingData = transactionMapper.transactionCreateDtoToTransactionProcessingData(transactionCreateDTO);
    processingData.setCardId(cardId);

    try {
      processingData.setCardId(cardId);
      processingData.setFromAccountId(transactionCreateDTO.getFromAccountId());
      processingData.setToAccountId(transactionCreateDTO.getToAccountId());

      if(processingData.getPaymentType().name().equals(PaymentType.CREDIT.name()) &&
              transactionCreateDTO.getInstallmentValue() != null &&
              transactionCreateDTO.getNumberInstallment() != null
      ) {
        Date dueDate = DateUtils.addMonths(new Date(), transactionCreateDTO.getNumberInstallment());
        InstallmentData installmentData = new InstallmentData(
                transactionCreateDTO.getNumberInstallment(),
                transactionCreateDTO.getInstallmentValue(),
                dueDate
        );
        processingData.setInstallmentData(installmentData);
      }
      List<Transaction> transactions = transactionDomainService.processTransaction(processingData);
      return transactions.stream().map(transactionMapper::transactionToTransactionResponseDTO).toList();
    } catch (BadRequestException e) {
      throw new RuntimeException("Wrong endpoint to make transfer: ", e);
    }
  }

  @Transactional
  public List<Transaction> createTransfer(TransactionProcessingData processingData) {
    log.info("APPLICATION: Request received from controller and passing to domain to create a new transfer");
    try {
      return transactionDomainService.processTransaction(processingData);
    } catch (BadRequestException e) {
      throw new RuntimeException("Wrong endpoint to make transfer: ", e);
    }
  }

  public void deleteTransaction(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to domain to delete a transaction by id: {}", id);
    transactionDomainService.deleteTransaction(id);
  }
}
