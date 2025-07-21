package br.com.gritti.app.domain.service.strategy.impl;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.service.BankAccountDomainService;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategy;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.shared.exceptions.InvalidBalanceException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransferTransactionStrategy implements TransactionProcessingStrategy {
  private final BankAccountDomainService bankAccountDomainService;

  public TransferTransactionStrategy(BankAccountDomainService bankAccountDomainService) {
    this.bankAccountDomainService = bankAccountDomainService;
  }

  @Override
  public List<Transaction> processTransaction(Transaction transaction, TransactionProcessingData processingData)
          throws InvalidBalanceException {
    if (processingData.getFromAccountId() == null || processingData.getToAccountId() == null) {
      throw new IllegalArgumentException("From account id and to account id are required for transfer transactions");
    }

    BankAccount sourceAccount = bankAccountDomainService.getAccountById(processingData.getFromAccountId());
    BankAccount targetAccount = bankAccountDomainService.getAccountById(processingData.getToAccountId());

    if (transaction.getValue() > sourceAccount.getBalance()) {
      throw new InvalidBalanceException("Insufficient balance from account: " + sourceAccount.getId());
    }

    sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getValue());
    targetAccount.setBalance(targetAccount.getBalance() + transaction.getValue());

    bankAccountDomainService.updateAccount(sourceAccount.getId(), sourceAccount);
    bankAccountDomainService.updateAccount(targetAccount.getId(), targetAccount);

    transaction.setTimestamp(new Date());
    return Collections.singletonList(transaction);
  }
}
