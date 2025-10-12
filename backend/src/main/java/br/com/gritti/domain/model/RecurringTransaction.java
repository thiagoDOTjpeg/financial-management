package br.com.gritti.domain.model;
import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.RecurringFrequency;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recurring_transactions")
public class RecurringTransaction extends AuditableEntity{
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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RecurringFrequency frequency;

  @Column(name = "day_of_month")
  private Integer dayOfMonth;

  @Column(name = "day_of_week")
  private Integer dayOfWeek;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type", nullable = false, length = 20)
  private PaymentType paymentType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bank_account_id")
  private BankAccount bankAccount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @OneToMany(mappedBy = "recurringTransaction")
  private Set<Transaction> generatedTransactions = new HashSet<>();

  public boolean isInfinite() {
    return endDate == null;
  }

  public RecurringTransaction() {
  }

  private RecurringTransaction(Builder builder) {
    this.user = builder.user;
    this.category = builder.category;
    this.description = builder.description;
    this.amount = builder.amount;
    this.frequency = builder.frequency;
    this.dayOfMonth = builder.dayOfMonth;
    this.dayOfWeek = builder.dayOfWeek;
    this.paymentType = builder.paymentType;
    this.bankAccount = builder.bankAccount;
    this.card = builder.card;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.isActive = builder.isActive;
    this.generatedTransactions = builder.generatedTransactions;
  }

  public static class Builder {
    private User user;
    private Category category;
    private String description;
    private BigDecimal amount;
    private RecurringFrequency frequency;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private PaymentType paymentType;
    private BankAccount bankAccount;
    private Card card;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Set<Transaction> generatedTransactions = new HashSet<>();

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

    public Builder frequency(RecurringFrequency frequency) {
      this.frequency = frequency;
      return this;
    }

    public Builder dayOfMonth(Integer dayOfMonth) {
      this.dayOfMonth = dayOfMonth;
      return this;
    }

    public Builder dayOfWeek(Integer dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
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

    public Builder card(Card card) {
      this.card = card;
      return this;
    }

    public Builder startDate(LocalDate startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder endDate(LocalDate endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder generatedTransactions(Set<Transaction> generatedTransactions) {
      this.generatedTransactions = generatedTransactions;
      return this;
    }

    public RecurringTransaction build() {
      return new RecurringTransaction(this);
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

  public RecurringFrequency getFrequency() {
    return frequency;
  }

  public void setFrequency(RecurringFrequency frequency) {
    this.frequency = frequency;
  }

  public Integer getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Integer getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(Integer dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
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

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Set<Transaction> getGeneratedTransactions() {
    return generatedTransactions;
  }

  public void setGeneratedTransactions(Set<Transaction> generatedTransactions) {
    this.generatedTransactions = generatedTransactions;
  }
}
