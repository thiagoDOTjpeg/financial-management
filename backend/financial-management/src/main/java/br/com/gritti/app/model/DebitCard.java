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
public class DebitCard implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

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

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(mappedBy = "debitCard")
  private List<DebitTransaction> transactions;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public List<DebitTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<DebitTransaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    DebitCard debitCard = (DebitCard) o;
    return Objects.equals(id, debitCard.id) && Objects.equals(createdAt, debitCard.createdAt) && Objects.equals(createdBy, debitCard.createdBy) && Objects.equals(updatedAt, debitCard.updatedAt) && Objects.equals(updatedBy, debitCard.updatedBy) && Objects.equals(account, debitCard.account) && Objects.equals(transactions, debitCard.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, createdBy, updatedAt, updatedBy, account, transactions);
  }
}
