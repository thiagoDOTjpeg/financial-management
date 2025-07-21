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
import br.com.gritti.app.infra.repository.InvoiceRepositoryImpl;
import br.com.gritti.app.infra.repository.TransactionRepositoryImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class InstallmentTransactionStrategy implements TransactionProcessingStrategy {
  private final CardDomainService cardDomainService;
  private final InvoiceDomainService invoiceDomainService;
  private final InvoiceRepositoryImpl invoiceRepositoryImpl;
  private final TransactionRepositoryImpl transactionRepositoryImpl;
  private final InstallmentDomainService installmentDomainService;

  public InstallmentTransactionStrategy(CardDomainService cardDomainService,
                                        InvoiceDomainService invoiceDomainService,
                                        InstallmentDomainService installmentDomainService,
                                        InvoiceRepositoryImpl invoiceRepositoryImpl,
                                        TransactionRepositoryImpl transactionRepositoryImpl) {
    this.cardDomainService = cardDomainService;
    this.transactionRepositoryImpl = transactionRepositoryImpl;
    this.invoiceRepositoryImpl = invoiceRepositoryImpl;
    this.invoiceDomainService = invoiceDomainService;
    this.installmentDomainService = installmentDomainService;
  }

  @Override
  public List<Transaction> processTransaction(Transaction transaction, TransactionProcessingData processingData) {
    if (processingData.getCardId() == null) {
      throw new IllegalArgumentException("Card id is required for installment transactions");
    }

    Card card = cardDomainService.getCardById(processingData.getCardId());
    Installment installment = installmentDomainService.createInstallment(processingData.getInstallmentData());

    List<Transaction> transactionToCreate = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    cal.setTime(transaction.getTimestamp());

    for (int i = 0; i < installment.getNumberInstallment(); i++) {
      if (i > 0) {
        cal.add(Calendar.MONTH, 1);
      }
      Date installmentDate = cal.getTime();
      Invoice invoiceForInstallment = invoiceDomainService.getOrCreateInvoiceForDate(cal.getTime(), card);

      Transaction installmentTransaction = new Transaction();
      installmentTransaction.setCategory(transaction.getCategory());
      installmentTransaction.setPaymentType(transaction.getPaymentType());
      installmentTransaction.setTimestamp(installmentDate);
      installmentTransaction.setValue(installment.getInstallmentValue());
      installmentTransaction.setInstallment(installment);
      installmentTransaction.setInvoice(invoiceForInstallment);

      invoiceForInstallment.setTotalValue(invoiceForInstallment.getTotalValue() + installmentTransaction.getValue());
      invoiceForInstallment.getInstallments().add(installment);
      invoiceRepositoryImpl.save(invoiceForInstallment);
      transactionRepositoryImpl.save(installmentTransaction);
      transactionToCreate.add(installmentTransaction);
    }
    return transactionToCreate;
  }
}
