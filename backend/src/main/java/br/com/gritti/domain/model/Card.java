package br.com.gritti.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cards")
public class Card {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bank_account_id", nullable = false)
  private BankAccount bankAccount;

  @Column(name = "card_brand", nullable = false, length = 50)
  private String cardBrand;

  @Column(name = "card_name", length = 100)
  private String cardName;

  @Column(name = "last_four_digits", length = 4)
  private String lastFourDigits;

  @Column(name = "credit_limit", nullable = false, precision = 12, scale = 2)
  private BigDecimal creditLimit;

  @Column(name = "closing_day", nullable = false)
  private Integer closingDay;

  @Column(name = "due_day", nullable = false)
  private Integer dueDay;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Invoice> invoices = new HashSet<>();

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Installment> installments = new HashSet<>();

  @PrePersist
  @PreUpdate
  private void validateDays() {
    if (closingDay < 1 || closingDay > 31) {
      throw new IllegalArgumentException("Closing day must be between 1 and 31");
    }
    if (dueDay < 1 || dueDay > 31) {
      throw new IllegalArgumentException("Due day must be between 1 and 31");
    }
  }

  public Card() {
  }

  private Card(Builder builder) {
    this.bankAccount = builder.bankAccount;
    this.cardBrand = builder.cardBrand;
    this.cardName = builder.cardName;
    this.lastFourDigits = builder.lastFourDigits;
    this.creditLimit = builder.creditLimit;
    this.closingDay = builder.closingDay;
    this.dueDay = builder.dueDay;
    this.isActive = builder.isActive;
    this.invoices = builder.invoices;
    this.installments = builder.installments;
  }

  public static class Builder {
    private BankAccount bankAccount;
    private String cardBrand;
    private String cardName;
    private String lastFourDigits;
    private BigDecimal creditLimit;
    private Integer closingDay;
    private Integer dueDay;
    private Boolean isActive;
    private Set<Invoice> invoices = new HashSet<>();
    private Set<Installment> installments = new HashSet<>();

    public Builder bankAccount(BankAccount bankAccount) {
      this.bankAccount = bankAccount;
      return this;
    }

    public Builder cardBrand(String cardBrand) {
      this.cardBrand = cardBrand;
      return this;
    }

    public Builder cardName(String cardName) {
      this.cardName = cardName;
      return this;
    }

    public Builder lastFourDigits(String lastFourDigits) {
      this.lastFourDigits = lastFourDigits;
      return this;
    }

    public Builder creditLimit(BigDecimal creditLimit) {
      this.creditLimit = creditLimit;
      return this;
    }

    public Builder closingDay(Integer closingDay) {
      this.closingDay = closingDay;
      return this;
    }

    public Builder dueDay(Integer dueDay) {
      this.dueDay = dueDay;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder invoices(Set<Invoice> invoices) {
      this.invoices = invoices;
      return this;
    }

    public Builder installments(Set<Installment> installments) {
      this.installments = installments;
      return this;
    }

    public Card build() {
      return new Card(this);
    }
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(BankAccount bankAccount) {
    this.bankAccount = bankAccount;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getLastFourDigits() {
    return lastFourDigits;
  }

  public void setLastFourDigits(String lastFourDigits) {
    this.lastFourDigits = lastFourDigits;
  }

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }

  public Integer getClosingDay() {
    return closingDay;
  }

  public void setClosingDay(Integer closingDay) {
    this.closingDay = closingDay;
  }

  public Integer getDueDay() {
    return dueDay;
  }

  public void setDueDay(Integer dueDay) {
    this.dueDay = dueDay;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Set<Invoice> getInvoices() {
    return invoices;
  }

  public void setInvoices(Set<Invoice> invoices) {
    this.invoices = invoices;
  }

  public Set<Installment> getInstallments() {
    return installments;
  }

  public void setInstallments(Set<Installment> installments) {
    this.installments = installments;
  }
}
