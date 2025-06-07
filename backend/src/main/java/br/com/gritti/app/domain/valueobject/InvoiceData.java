package br.com.gritti.app.domain.valueobject;

import java.util.Date;

public class InvoiceData {
  private Date billingMonth;

  public InvoiceData() {
  }

  public InvoiceData(Date billingMonth) {
    this.billingMonth = billingMonth;
  }

  public Date getBillingMonth() {
    return billingMonth;
  }

  public void setBillingMonth(Date billingMonth) {
    this.billingMonth = billingMonth;
  }
}
