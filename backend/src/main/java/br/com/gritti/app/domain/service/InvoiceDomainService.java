package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Invoice;
import br.com.gritti.app.domain.valueobject.InvoiceData;
import br.com.gritti.app.infra.repository.InvoiceRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class InvoiceDomainService {

  private final InvoiceRepositoryImpl invoiceRepositoryImpl;

  @Autowired
  public InvoiceDomainService(InvoiceRepositoryImpl invoiceRepositoryImpl) {
    this.invoiceRepositoryImpl = invoiceRepositoryImpl;
  }

  public Invoice getInvoiceById(UUID id) {
    return invoiceRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice with id " + id + " not found"));
  }

  public Invoice getOrCreateInvoiceForDate(Date date, Card card) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date currentBillingMonth = cal.getTime();

    Optional<Invoice> existingInvoice = invoiceRepositoryImpl.findByBillingMonthAndCardAndStatus(currentBillingMonth, card, InvoiceStatus.OPEN);
    if(existingInvoice.isPresent()){
      Invoice invoice = existingInvoice.get();

      long daysToClosure = getDaysDifference(new Date(), invoice.getClosingDate());

      if(daysToClosure <= 1){
        return getOrCreateNextMonthInvoice(currentBillingMonth, card);
      } else {
        return invoice;
      }
    } else {
      Optional<Invoice> closedInvoice = invoiceRepositoryImpl.findByBillingMonthAndCardAndStatus(currentBillingMonth, card, InvoiceStatus.CLOSED);

      if(closedInvoice.isPresent()){
        return getOrCreateNextMonthInvoice(currentBillingMonth, card);
      } else {
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setBillingMonth(currentBillingMonth);

        return createNewInvoice(invoiceData, card);
      }
    }
  }

  private Invoice getOrCreateNextMonthInvoice(Date currentBillingMonth, Card card) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentBillingMonth);
    cal.add(Calendar.MONTH, 1);
    Date nextBillingMonth = cal.getTime();

    Optional<Invoice> nextInvoice = invoiceRepositoryImpl.findByBillingMonthAndCardAndStatus(nextBillingMonth, card, InvoiceStatus.OPEN);

    if(nextInvoice.isPresent()){
      return nextInvoice.get();
    } else {
      InvoiceData invoiceData = new InvoiceData();
      invoiceData.setBillingMonth(nextBillingMonth);
      return createNewInvoice(invoiceData, card);
    }
  }

  private Invoice createNewInvoice(InvoiceData invoiceData, Card card) {
    Invoice newInvoice = new Invoice();
    newInvoice.setBillingMonth(invoiceData.getBillingMonth());
    newInvoice.setCard(card);
    newInvoice.setStatus(InvoiceStatus.OPEN);
    newInvoice.setTotalValue(0.0);

    Calendar cal = Calendar.getInstance();
    cal.setTime(newInvoice.getBillingMonth());

    cal.set(Calendar.DAY_OF_MONTH, card.getClosingDay());
    newInvoice.setClosingDate(cal.getTime());

    cal.add(Calendar.MONTH, 1);
    cal.set(Calendar.DAY_OF_MONTH, card.getDueDay());
    newInvoice.setDueDate(cal.getTime());

    invoiceRepositoryImpl.save(newInvoice);
    return newInvoice;
  }

  private long getDaysDifference(Date startDate, Date endDate) {
    long diffInMillis = Math.abs(endDate.getTime() - startDate.getTime());
    return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
  }
}
