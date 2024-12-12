package br.com.gritti.app.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "transaction")
public class Transaction implements Serializable  {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_account")
  private Account id_account;

  @ManyToOne
  @JoinColumn(name = "id_card")
  private CreditCard id_card;

  @Column(name = "name_transaction", nullable = false)
  private String name_transaction;

  @Column(name = "value", nullable = false)
  private double value;

  @Column(name = "timestamp", nullable = false)
  private Date timestamp;

  @Column(name = "description")
  private String description;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Account getId_account() {
    return id_account;
  }

  public void setId_account(Account id_account) {
    this.id_account = id_account;
  }

  public CreditCard getId_card() {
    return id_card;
  }

  public void setId_card(CreditCard id_card) {
    this.id_card = id_card;
  }

  public String getName_transaction() {
    return name_transaction;
  }

  public void setName_transaction(String name_transaction) {
    this.name_transaction = name_transaction;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Double.compare(value, that.value) == 0 && Objects.equals(id, that.id) && Objects.equals(id_account, that.id_account) && Objects.equals(id_card, that.id_card) && Objects.equals(name_transaction, that.name_transaction) && Objects.equals(timestamp, that.timestamp) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, id_account, id_card, name_transaction, value, timestamp, description);
  }
}