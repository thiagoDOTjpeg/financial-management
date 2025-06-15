package br.com.gritti.app.domain.service.strategy.impl;

import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Invoice;
import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.service.CardDomainService;
import br.com.gritti.app.domain.service.InvoiceDomainService;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategy;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import org.springframework.stereotype.Component;

@Component
public class CreditTransactionStrategy implements TransactionProcessingStrategy {
  private final CardDomainService cardDomainService;
  private final InvoiceDomainService invoiceDomainService;

  public CreditTransactionStrategy(CardDomainService cardDomainService,
                                   InvoiceDomainService invoiceDomainService) {
    this.cardDomainService = cardDomainService;
    this.invoiceDomainService = invoiceDomainService;
  }

  @Override
  public void processTransaction(Transaction transaction, TransactionProcessingData processingData) {
    if (processingData.getCardId() == null) {
      throw new IllegalArgumentException("Card id is required for credit transactions");
    }

    Card card = cardDomainService.getCardById(processingData.getCardId());
    Invoice invoice = invoiceDomainService.getOrCreateInvoiceForDate(transaction.getTimestamp(), card);
    transaction.setInvoice(invoice);
  }
}
