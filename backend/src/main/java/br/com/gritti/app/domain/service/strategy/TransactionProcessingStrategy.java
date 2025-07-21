package br.com.gritti.app.domain.service.strategy;

import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;

import java.util.List;

public interface TransactionProcessingStrategy {
  List<Transaction> processTransaction(Transaction transaction, TransactionProcessingData transactionProcessingData);
}
