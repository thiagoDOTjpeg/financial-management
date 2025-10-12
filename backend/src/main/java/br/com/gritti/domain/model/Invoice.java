package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.InvoiceStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invoices")
public class Invoice extends AuditableEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Column(name = "billing_month", nullable = false)
  private LocalDate billingMonth;

  @Column(name = "closing_date", nullable = false)
  private LocalDate closingDate;

  @Column(name = "due_date", nullable = false)
  private LocalDate dueDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private InvoiceStatus status = InvoiceStatus.OPEN;

  @Column(name = "paid_at")
  private LocalDateTime paidAt;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();

  public void markAsPaid() {
    this.status = InvoiceStatus.PAID;
    this.paidAt = LocalDateTime.now();
  }

  public void close() {
    if (this.status == InvoiceStatus.OPEN) {
      this.status = InvoiceStatus.CLOSED;
    }
  }

  public Invoice() {
  }

  private Invoice(Builder builder) {
    this.card = builder.card;
    this.billingMonth = builder.billingMonth;
    this.closingDate = builder.closingDate;
    this.dueDate = builder.dueDate;
    this.status = builder.status;
    this.paidAt = builder.paidAt;
    this.transactions = builder.transactions;
  }

  public static class Builder {
    private Card card;
    private LocalDate billingMonth;
    private LocalDate closingDate;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private LocalDateTime paidAt;
    private Set<Transaction> transactions = new HashSet<>();

    public Builder card(Card card) {
      this.card = card;
      return this;
    }

    public Builder billingMonth(LocalDate billingMonth) {
      this.billingMonth = billingMonth;
      return this;
    }

    public Builder closingDate(LocalDate closingDate) {
      this.closingDate = closingDate;
      return this;
    }

    public Builder dueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public Builder status(InvoiceStatus status) {
      this.status = status;
      return this;
    }

    public Builder paidAt(LocalDateTime paidAt) {
      this.paidAt = paidAt;
      return this;
    }

    public Builder transactions(Set<Transaction> transactions) {
      this.transactions = transactions;
      return this;
    }

    public Invoice build() {
      return new Invoice(this);
    }
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
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

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }
}
