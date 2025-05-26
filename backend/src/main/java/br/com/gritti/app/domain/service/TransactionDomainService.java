package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.infra.repository.TransactionRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionDomainService {
  private final Logger log = LoggerFactory.getLogger(TransactionDomainService.class);

  private final TransactionRepositoryImpl transactionRepositoryImpl;

  @Autowired
  public TransactionDomainService(TransactionRepositoryImpl transactionRepositoryImpl) {
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

  public Transaction save(Transaction transaction) {
    log.info("DOMAIN: Request received from application and saving a new transaction from the repository");
    transactionRepositoryImpl.save(transaction);
    return transaction;
  }

  public void delete(UUID id) {
    log.info("DOMAIN: Request received from application and deleting transaction with id {}", id);
    Transaction transaction = transactionRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id " + id + " not found"));
    transactionRepositoryImpl.delete(transaction);
  }
}
