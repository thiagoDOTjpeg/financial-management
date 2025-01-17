package br.com.gritti.app.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account extends Auditable implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "bank_name")
  private String bankName;

  @Column
  private Double balance;

  @OneToMany(mappedBy = "account")
  private List<CreditCard> creditCards;

  @OneToMany(mappedBy = "account")
  private List<DebitCard> debitCards;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public List<CreditCard> getCreditCards() {
    return creditCards;
  }

  public void setCreditCards(List<CreditCard> creditCard) {
    this.creditCards = creditCard;
  }

  public List<DebitCard> getDebitCards() {
    return debitCards;
  }

  public void setDebitCards(List<DebitCard> debitCard) {
    this.debitCards = debitCard;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(id, account.id) && Objects.equals(user, account.user) && Objects.equals(bankName, account.bankName) && Objects.equals(balance, account.balance) && Objects.equals(creditCards, account.creditCards) && Objects.equals(debitCards, account.debitCards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, bankName, balance, creditCards, debitCards);
  }
}
