package br.com.gritti.app.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends Auditable implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "card_brand")
  private String cardBrand;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(mappedBy = "creditCard")
  private List<Invoice> invoices;

  @OneToMany(mappedBy = "creditCard")
  private List<CreditTransaction> transactions;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Invoice> getInvoices() {
    return invoices;
  }

  public void setInvoices(List<Invoice> invoices) {
    this.invoices = invoices;
  }

  public List<CreditTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<CreditTransaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CreditCard that = (CreditCard) o;
    return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(cardBrand, that.cardBrand) && Objects.equals(account, that.account) && Objects.equals(invoices, that.invoices) && Objects.equals(transactions, that.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, cardBrand, account, invoices, transactions);
  }
}
