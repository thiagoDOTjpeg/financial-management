package br.com.gritti.app.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CreditTransaction implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Double value;

  @Column(name = "number_of_installments")
  private int numberOfInstallments;

  @Column
  private String description;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "credit_card_id")
  private CreditCard creditCard;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public int getNumberOfInstallments() {
    return numberOfInstallments;
  }

  public void setNumberOfInstallments(int numberOfInstallments) {
    this.numberOfInstallments = numberOfInstallments;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CreditTransaction that = (CreditTransaction) o;
    return numberOfInstallments == that.numberOfInstallments && Objects.equals(id, that.id) && Objects.equals(value, that.value) && Objects.equals(description, that.description) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(creditCard, that.creditCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, numberOfInstallments, description, createdAt, createdBy, updatedAt, updatedBy, creditCard);
  }
}
