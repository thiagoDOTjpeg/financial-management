package br.com.gritti.app.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
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

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private Date createdAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updatedAt;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;

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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
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
    return Objects.equals(id, account.id) && Objects.equals(bankName, account.bankName) && Objects.equals(balance, account.balance) && Objects.equals(createdAt, account.createdAt) && Objects.equals(createdBy, account.createdBy) && Objects.equals(updatedAt, account.updatedAt) && Objects.equals(updatedBy, account.updatedBy) && Objects.equals(creditCards, account.creditCards) && Objects.equals(debitCards, account.debitCards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bankName, balance, createdAt, createdBy, updatedAt, updatedBy, creditCards, debitCards);
  }
}
