package br.com.gritti.app.application.dto.invoice;

import java.util.Date;

public class InvoiceCreateDTO {
  private Date billingMonth;
  public InvoiceCreateDTO() {
  }

  public InvoiceCreateDTO(Date billingMonth, Integer dueDate, Integer closingDay) {
    this.billingMonth = billingMonth;
  }

  public Date getBillingMonth() {
    return billingMonth;
  }

  public void setBillingMonth(Date billingMonth) {
    this.billingMonth = billingMonth;
  }

}
