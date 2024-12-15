package br.com.gritti.app.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_card")
  private CreditCard id_card;

  @Column(name = "total_value")
  private Double total_value;

  @Column(name = "paid")
  private boolean paid;

  @Column(name = "reference_month")
  private String reference_month;

  @Column(name = "reference_year")
  private String reference_year;

  @OneToMany
  @JoinColumn(name = "id_transaction")
  private List<Transaction> id_transaction;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CreditCard getId_card() {
    return id_card;
  }

  public void setId_card(CreditCard id_card) {
    this.id_card = id_card;
  }

  public Double getTotal_value() {
    return total_value;
  }

  public void setTotal_value(Double total_value) {
    this.total_value = total_value;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  public String getReference_month() {
    return reference_month;
  }

  public void setReference_month(String reference_month) {
    this.reference_month = reference_month;
  }

  public String getReference_year() {
    return reference_year;
  }

  public void setReference_year(String reference_year) {
    this.reference_year = reference_year;
  }

  public List<Transaction> getId_transaction() {
    return id_transaction;
  }

  public void setId_transaction(List<Transaction> id_transaction) {
    this.id_transaction = id_transaction;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Invoice invoice = (Invoice) o;
    return paid == invoice.paid && Objects.equals(id, invoice.id) && Objects.equals(id_card, invoice.id_card) && Objects.equals(total_value, invoice.total_value) && Objects.equals(reference_month, invoice.reference_month) && Objects.equals(reference_year, invoice.reference_year) && Objects.equals(id_transaction, invoice.id_transaction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, id_card, total_value, paid, reference_month, reference_year, id_transaction);
  }
}
