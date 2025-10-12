package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.AccountType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends AuditableEntity{
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "bank_name", nullable = false, length = 100)
  private String bankName;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type", nullable = false, length = 20)
  private AccountType accountType = AccountType.CHECKING;

  @Column(name = "account_number", length = 20)
  private String accountNumber;

  @Column(length = 10)
  private String agency;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Card> cards = new HashSet<>();

  @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();

  public void addCard(Card card) {
    cards.add(card);
    card.setBankAccount(this);
  }

  public void removeCard(Card card) {
    cards.remove(card);
    card.setBankAccount(null);
  }

  public BankAccount() {
  }

  private BankAccount(Builder builder) {
    this.user = builder.user;
    this.bankName = builder.bankName;
    this.accountType = builder.accountType;
    this.accountNumber = builder.accountNumber;
    this.agency = builder.agency;
    this.isActive = builder.isActive;
    this.cards = builder.cards;
    this.transactions = builder.transactions;
  }

  public static class Builder {
    private User user;
    private String bankName;
    private AccountType accountType;
    private String accountNumber;
    private String agency;
    private Boolean isActive;
    private Set<Card> cards = new HashSet<>();
    private Set<Transaction> transactions = new HashSet<>();

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder bankName(String bankName) {
      this.bankName = bankName;
      return this;
    }

    public Builder accountType(AccountType accountType) {
      this.accountType = accountType;
      return this;
    }

    public Builder accountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
      return this;
    }

    public Builder agency(String agency) {
      this.agency = agency;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder cards(Set<Card> cards) {
      this.cards = cards;
      return this;
    }

    public Builder transactions(Set<Transaction> transactions) {
      this.transactions = transactions;
      return this;
    }

    public BankAccount build() {
      return new BankAccount(this);
    }
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAgency() {
    return agency;
  }

  public void setAgency(String agency) {
    this.agency = agency;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Set<Card> getCards() {
    return cards;
  }

  public void setCards(Set<Card> cards) {
    this.cards = cards;
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }
}
