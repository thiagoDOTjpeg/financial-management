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
@Table(name = "debit_transactions")
public class DebitTransaction extends Auditable implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private Double value;

  @Column
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "debit_card_id")
  private DebitCard debitCard;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DebitCard getDebitCard() {
    return debitCard;
  }

  public void setDebitCard(DebitCard debitCard) {
    this.debitCard = debitCard;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    DebitTransaction that = (DebitTransaction) o;
    return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(value, that.value) && Objects.equals(description, that.description) && Objects.equals(debitCard, that.debitCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, value, description, debitCard);
  }
}
