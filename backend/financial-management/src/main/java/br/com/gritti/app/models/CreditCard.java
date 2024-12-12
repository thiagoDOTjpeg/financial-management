package br.com.gritti.app.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "credit_card")
public class CreditCard {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_account")
  private Account id_account;

  @Column(name = "closing_date")
  private String closing_date;

  @Column(name = "due_date")
  private String due_date;

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

  public String getClosing_date() {
    return closing_date;
  }

  public void setClosing_date(String closing_date) {
    this.closing_date = closing_date;
  }

  public String getDue_date() {
    return due_date;
  }

  public void setDue_date(String due_date) {
    this.due_date = due_date;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CreditCard that = (CreditCard) o;
    return Objects.equals(id, that.id) && Objects.equals(id_account, that.id_account) && Objects.equals(closing_date, that.closing_date) && Objects.equals(due_date, that.due_date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, id_account, closing_date, due_date);
  }
}
