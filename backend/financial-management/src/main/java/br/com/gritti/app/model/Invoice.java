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
@Table(name = "invoices")
public class Invoice implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "due_data")
  private Date dueDate;

  @Column
  private String status;

  @Column(name = "reference_month")
  private String referenceMonth;

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

  @OneToMany(mappedBy = "invoice")
  private List<CreditTransaction> transactions;

  @ManyToOne
  @JoinColumn(name = "credit_card_id")
  private CreditCard creditCard;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReferenceMonth() {
    return referenceMonth;
  }

  public void setReferenceMonth(String referenceMonth) {
    this.referenceMonth = referenceMonth;
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

  public List<CreditTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<CreditTransaction> transactions) {
    this.transactions = transactions;
  }

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Invoice invoice = (Invoice) o;
    return Objects.equals(id, invoice.id) && Objects.equals(user, invoice.user) && Objects.equals(dueDate, invoice.dueDate) && Objects.equals(status, invoice.status) && Objects.equals(referenceMonth, invoice.referenceMonth) && Objects.equals(createdAt, invoice.createdAt) && Objects.equals(createdBy, invoice.createdBy) && Objects.equals(updatedAt, invoice.updatedAt) && Objects.equals(updatedBy, invoice.updatedBy) && Objects.equals(transactions, invoice.transactions) && Objects.equals(creditCard, invoice.creditCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, dueDate, status, referenceMonth, createdAt, createdBy, updatedAt, updatedBy, transactions, creditCard);
  }
}
