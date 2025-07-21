package br.com.gritti.app.domain.service;

import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.domain.model.*;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategy;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategyFactory;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.infra.repository.InvoiceRepositoryImpl;
import br.com.gritti.app.infra.repository.TransactionRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionDomainService {
  private final Logger log = LoggerFactory.getLogger(TransactionDomainService.class);

  private final TransactionRepositoryImpl transactionRepositoryImpl;
  private final InvoiceRepositoryImpl invoiceRepositoryImpl;
  private final TransactionMapper transactionMapper;
  private final TransactionProcessingStrategyFactory strategyFactory;

  @Autowired
  public TransactionDomainService(TransactionRepositoryImpl transactionRepositoryImpl, TransactionProcessingStrategyFactory strategyFactory,
                                  TransactionMapper transactionMapper, InvoiceRepositoryImpl invoiceRepositoryImpl) {
    this.transactionRepositoryImpl = transactionRepositoryImpl;
    this.invoiceRepositoryImpl = invoiceRepositoryImpl;
    this.transactionMapper = transactionMapper;
    this.strategyFactory = strategyFactory;
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

  public Page<Transaction> getInvoiceTransactions(Pageable pageable, Invoice invoice) {
    log.info("DOMAIN: Request received from controller and passing to get all transactions from the repository");
    return transactionRepositoryImpl.findTransactionByInvoice(pageable, invoice);
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
  public List<Transaction> processTransaction(TransactionProcessingData transactionProcessingData) throws BadRequestException {
    log.info("DOMAIN: Processing transaction with payment type: {}", transactionProcessingData.getPaymentType());
    TransactionProcessingStrategy strategy = strategyFactory.getStrategy(transactionProcessingData.getPaymentType(), transactionProcessingData);
    Transaction transaction = transactionMapper.transactionProcessingDataToTransaction(transactionProcessingData);
    List<Transaction> transactionsToProcess = strategy.processTransaction(transaction, transactionProcessingData);
    transactionsToProcess.forEach( t -> {
      if(t.getInvoice() != null){
        invoiceRepositoryImpl.save(t.getInvoice());
      }
      createTransaction(t);
    });
    return transactionsToProcess;
  }
}
