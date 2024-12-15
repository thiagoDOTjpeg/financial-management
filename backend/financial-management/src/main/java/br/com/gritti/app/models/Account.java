package br.com.gritti.app.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "balance")
  private Double balance;

  @Column(name = "bank")
  private String bank;

  @OneToMany
  @JoinColumn(name = "id_card")
  private List<CreditCard> id_card;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public String getBank() {
    return bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public List<CreditCard> getId_card() {
    return id_card;
  }

  public void setId_card(List<CreditCard> id_card) {
    this.id_card = id_card;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(id, account.id) && Objects.equals(balance, account.balance) && Objects.equals(bank, account.bank) && Objects.equals(id_card, account.id_card);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, balance, bank, id_card);
  }

  @OneToMany(mappedBy = "id_account")
  private Collection<Transaction> transaction;

  public Collection<Transaction> getTransaction() {
    return transaction;
  }

  public void setTransaction(Collection<Transaction> transaction) {
    this.transaction = transaction;
  }
}
