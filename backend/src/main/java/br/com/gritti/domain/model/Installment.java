package br.com.gritti.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "installments")
public class Installment extends AuditableEntity{
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Column(nullable = false)
  private String description;

  @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "total_installments", nullable = false)
  private Integer totalInstallments;

  @Column(name = "installment_value", nullable = false, precision = 12, scale = 2)
  private BigDecimal installmentValue;

  @Column(name = "first_due_date", nullable = false)
  private LocalDate firstDueDate;

  @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();

  @PrePersist
  @PreUpdate
  private void validate() {
    if (totalInstallments < 1) {
      throw new IllegalArgumentException("Total installments must be at least 1");
    }
  }

  public Installment() {
  }

  private Installment(Builder builder) {
    this.card = builder.card;
    this.description = builder.description;
    this.totalAmount = builder.totalAmount;
    this.totalInstallments = builder.totalInstallments;
    this.installmentValue = builder.installmentValue;
    this.firstDueDate = builder.firstDueDate;
    this.transactions = builder.transactions;
  }

  public static class Builder {
    private Card card;
    private String description;
    private BigDecimal totalAmount;
    private Integer totalInstallments;
    private BigDecimal installmentValue;
    private LocalDate firstDueDate;
    private Set<Transaction> transactions = new HashSet<>();

    public Builder card(Card card) {
      this.card = card;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder totalAmount(BigDecimal totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    public Builder totalInstallments(Integer totalInstallments) {
      this.totalInstallments = totalInstallments;
      return this;
    }

    public Builder installmentValue(BigDecimal installmentValue) {
      this.installmentValue = installmentValue;
      return this;
    }

    public Builder firstDueDate(LocalDate firstDueDate) {
      this.firstDueDate = firstDueDate;
      return this;
    }

    public Builder transactions(Set<Transaction> transactions) {
      this.transactions = transactions;
      return this;
    }

    public Installment build() {
      return new Installment(this);
    }
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Integer getTotalInstallments() {
    return totalInstallments;
  }

  public void setTotalInstallments(Integer totalInstallments) {
    this.totalInstallments = totalInstallments;
  }

  public BigDecimal getInstallmentValue() {
    return installmentValue;
  }

  public void setInstallmentValue(BigDecimal installmentValue) {
    this.installmentValue = installmentValue;
  }

  public LocalDate getFirstDueDate() {
    return firstDueDate;
  }

  public void setFirstDueDate(LocalDate firstDueDate) {
    this.firstDueDate = firstDueDate;
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }
}
