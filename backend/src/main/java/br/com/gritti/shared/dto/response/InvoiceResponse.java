package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.InvoiceStatus;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Relation(collectionRelation = "invoice")
public class InvoiceResponse extends RepresentationModel<InvoiceResponse> {
  private CardResponse card;
  private LocalDate billingMonth;
  private LocalDate closingDate;
  private LocalDate dueDate;
  private InvoiceStatus status;
  private LocalDateTime paidAt;

  public InvoiceResponse() {
  }

  public InvoiceResponse(CardResponse card, LocalDate billingMonth, LocalDate closingDate, LocalDate dueDate, InvoiceStatus status, LocalDateTime paidAt) {
    this.card = card;
    this.billingMonth = billingMonth;
    this.closingDate = closingDate;
    this.dueDate = dueDate;
    this.status = status;
    this.paidAt = paidAt;
  }

  public CardResponse getCard() {
    return card;
  }

  public void setCard(CardResponse card) {
    this.card = card;
  }

  public LocalDate getBillingMonth() {
    return billingMonth;
  }

  public void setBillingMonth(LocalDate billingMonth) {
    this.billingMonth = billingMonth;
  }

  public LocalDate getClosingDate() {
    return closingDate;
  }

  public void setClosingDate(LocalDate closingDate) {
    this.closingDate = closingDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public InvoiceStatus getStatus() {
    return status;
  }

  public void setStatus(InvoiceStatus status) {
    this.status = status;
  }

  public LocalDateTime getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(LocalDateTime paidAt) {
    this.paidAt = paidAt;
  }
}
