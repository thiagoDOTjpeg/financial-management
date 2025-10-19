package br.com.gritti.domain.model;
import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction extends AuditableEntity{
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(name = "transaction_date", nullable = false)
  private LocalDate transactionDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", nullable = false, length = 10)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type", nullable = false, length = 20)
  private PaymentType paymentType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bank_account_id")
  private BankAccount bankAccount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "installment_id")
  private Installment installment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recurring_transaction_id")
  private RecurringTransaction recurringTransaction;

  @Column(name = "installment_number")
  private Integer installmentNumber;

  @Column(columnDefinition = "TEXT")
  private String notes;

  public boolean isInstallment() {
    return installment != null && installmentNumber != null;
  }

  public boolean isRecurring() {
    return recurringTransaction != null;
  }

  public String getInstallmentDisplay() {
    if (isInstallment()) {
      return installmentNumber + "/" + installment.getTotalInstallments();
    }
    return null;
  }

  public Transaction() {
  }

  private Transaction(Builder builder) {
    this.user = builder.user;
    this.category = builder.category;
    this.description = builder.description;
    this.amount = builder.amount;
    this.transactionDate = builder.transactionDate;
    this.transactionType = builder.transactionType;
    this.paymentType = builder.paymentType;
    this.bankAccount = builder.bankAccount;
    this.invoice = builder.invoice;
    this.installment = builder.installment;
    this.recurringTransaction = builder.recurringTransaction;
    this.installmentNumber = builder.installmentNumber;
    this.notes = builder.notes;
  }

  public static class Builder {
    private User user;
    private Category category;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionType transactionType;
    private PaymentType paymentType;
    private BankAccount bankAccount;
    private Invoice invoice;
    private Installment installment;
    private RecurringTransaction recurringTransaction;
    private Integer installmentNumber;
    private String notes;

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder category(Category category) {
      this.category = category;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder transactionDate(LocalDate transactionDate) {
      this.transactionDate = transactionDate;
      return this;
    }

    public Builder transactionType(TransactionType transactionType) {
      this.transactionType = transactionType;
      return this;
    }

    public Builder paymentType(PaymentType paymentType) {
      this.paymentType = paymentType;
      return this;
    }

    public Builder bankAccount(BankAccount bankAccount) {
      this.bankAccount = bankAccount;
      return this;
    }

    public Builder invoice(Invoice invoice) {
      this.invoice = invoice;
      return this;
    }

    public Builder installment(Installment installment) {
      this.installment = installment;
      return this;
    }

    public Builder recurringTransaction(RecurringTransaction recurringTransaction) {
      this.recurringTransaction = recurringTransaction;
      return this;
    }

    public Builder installmentNumber(Integer installmentNumber) {
      this.installmentNumber = installmentNumber;
      return this;
    }

    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public Transaction build() {
      return new Transaction(this);
    }
  }
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(BankAccount bankAccount) {
    this.bankAccount = bankAccount;
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  public Installment getInstallment() {
    return installment;
  }

  public void setInstallment(Installment installment) {
    this.installment = installment;
  }

  public RecurringTransaction getRecurringTransaction() {
    return recurringTransaction;
  }

  public void setRecurringTransaction(RecurringTransaction recurringTransaction) {
    this.recurringTransaction = recurringTransaction;
  }

  public Integer getInstallmentNumber() {
    return installmentNumber;
  }

  public void setInstallmentNumber(Integer installmentNumber) {
    this.installmentNumber = installmentNumber;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}

