package br.com.gritti.app.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "transaction")
public class Transaction  {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_account")
  private Account id_account;

  @Column
  private UUID id_invoice;

  @Column(nullable = false)
  private String name_transaction;

  @Column(nullable = false)
  private double value;

  @Column(nullable = false)
  private Date timestamp;

  @Column
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

  public UUID getId_invoice() {
    return id_invoice;
  }

  public void setId_invoice(UUID id_invoice) {
    this.id_invoice = id_invoice;
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
    return Double.compare(value, that.value) == 0 && Objects.equals(id, that.id) && Objects.equals(id_account, that.id_account) && Objects.equals(id_invoice, that.id_invoice) && Objects.equals(name_transaction, that.name_transaction) && Objects.equals(timestamp, that.timestamp) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, id_account, id_invoice, name_transaction, value, timestamp, description);
  }
}