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
@Table(name = "debit_cards")
public class DebitCard extends Auditable implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

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
    return Objects.equals(id, debitCard.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
