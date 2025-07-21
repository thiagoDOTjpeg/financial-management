package br.com.gritti.app.domain.service.strategy.impl;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.service.CardDomainService;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategy;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.shared.exceptions.InvalidBalanceException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DebitTransactionStrategy implements TransactionProcessingStrategy {
  private final CardDomainService cardDomainService;

  public DebitTransactionStrategy(CardDomainService cardDomainService) {
    this.cardDomainService = cardDomainService;
  }

  @Override
  public List<Transaction> processTransaction(Transaction transaction, TransactionProcessingData processingData)
          throws InvalidBalanceException {
    if (processingData.getCardId() == null) {
      throw new IllegalArgumentException("Card id is required for debit transactions");
    }

    Card card = cardDomainService.getCardById(processingData.getCardId());
    BankAccount account = card.getBankAccount();

    if (transaction.getValue() > account.getBalance()) {
      throw new InvalidBalanceException("Insufficient balance");
    }

    account.setBalance(account.getBalance() - transaction.getValue());
    card.setBankAccount(account);
    cardDomainService.updateCard(card.getId(), card);
    return Collections.singletonList(transaction);
  }
}
