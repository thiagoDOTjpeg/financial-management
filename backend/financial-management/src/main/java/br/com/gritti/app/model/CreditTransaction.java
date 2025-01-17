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
@Table(name = "credit_transactions")
public class CreditTransaction extends Auditable implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private Double value;

  @Column(name = "number_of_installments")
  private int numberOfInstallments;

  @Column
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "credit_card_id")
  private CreditCard creditCard;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

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

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CreditTransaction that = (CreditTransaction) o;
    return numberOfInstallments == that.numberOfInstallments && Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(value, that.value) && Objects.equals(description, that.description) && Objects.equals(creditCard, that.creditCard) && Objects.equals(invoice, that.invoice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, value, numberOfInstallments, description, creditCard, invoice);
  }
}
