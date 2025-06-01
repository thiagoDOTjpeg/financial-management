package br.com.gritti.app.application.dto.minimal;

import java.util.Date;
import java.util.UUID;

public class InvoiceMinimalDTO {
  private UUID id;
  private Date billingMonth;

  public InvoiceMinimalDTO() {
  }

  public InvoiceMinimalDTO(UUID id, Date billingMonth) {
    this.id = id;
    this.billingMonth = billingMonth;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getBillingMonth() {
    return billingMonth;
  }

  public void setBillingMonth(Date billingMonth) {
    this.billingMonth = billingMonth;
  }
}
