package br.com.gritti.app.domain.service.strategy;

import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;

public interface TransactionProcessingStrategy {
  void processTransaction(Transaction transaction, TransactionProcessingData transactionProcessingData);
}
