package br.com.gritti.app.services;

import br.com.gritti.app.models.Transaction;
import br.com.gritti.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {


  private final TransactionRepository transactionRepository;

  @Autowired
  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Page<Transaction> listAllTransaction(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return transactionRepository.findAll(pageable);
  }

  public Transaction createTransaction(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public void deleteTransaction(String id) throws Exception{
    var transaction = transactionRepository.findById(id).orElseThrow(() -> new Exception("Transaction not found"));
    transactionRepository.deleteById(transaction.getId());
  }


}
