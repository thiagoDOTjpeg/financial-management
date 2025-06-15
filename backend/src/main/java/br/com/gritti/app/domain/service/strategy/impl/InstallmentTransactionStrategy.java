package br.com.gritti.app.domain.service.strategy.impl;

import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Installment;
import br.com.gritti.app.domain.model.Invoice;
import br.com.gritti.app.domain.model.Transaction;
import br.com.gritti.app.domain.service.CardDomainService;
import br.com.gritti.app.domain.service.InstallmentDomainService;
import br.com.gritti.app.domain.service.InvoiceDomainService;
import br.com.gritti.app.domain.service.strategy.TransactionProcessingStrategy;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class InstallmentTransactionStrategy implements TransactionProcessingStrategy {
  private final CardDomainService cardDomainService;
  private final InvoiceDomainService invoiceDomainService;
  private final InstallmentDomainService installmentDomainService;

  public InstallmentTransactionStrategy(CardDomainService cardDomainService,
                                        InvoiceDomainService invoiceDomainService,
                                        InstallmentDomainService installmentDomainService) {
    this.cardDomainService = cardDomainService;
    this.invoiceDomainService = invoiceDomainService;
    this.installmentDomainService = installmentDomainService;
  }

  @Override
  public void processTransaction(Transaction transaction, TransactionProcessingData processingData) {
    if (processingData.getCardId() == null) {
      throw new IllegalArgumentException("Card id is required for installment transactions");
    }

    Card card = cardDomainService.getCardById(processingData.getCardId());
    Installment installment = installmentDomainService.createInstallment(processingData.getInstallmentData());
    transaction.setInstallment(installment);

    Calendar cal = Calendar.getInstance();
    cal.setTime(transaction.getTimestamp());

    for (int i = 0; i < installment.getNumberInstallment(); i++) {
      if (i > 0) {
        cal.add(Calendar.MONTH, 1);
      }

      Invoice invoiceForInstallment = invoiceDomainService.getOrCreateInvoiceForDate(cal.getTime(), card);
      invoiceForInstallment.getInstallment().add(installment);
    }

    Invoice firstInvoice = invoiceDomainService.getOrCreateInvoiceForDate(transaction.getTimestamp(), card);
    transaction.setInvoice(firstInvoice);
  }
}
