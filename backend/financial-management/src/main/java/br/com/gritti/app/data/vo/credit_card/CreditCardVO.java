package br.com.gritti.app.data.vo.credit_card;

import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.CreditCard;
import br.com.gritti.app.model.CreditTransaction;
import br.com.gritti.app.model.Invoice;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CreditCardVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private UserMinVO user;
  private String cardBrand;
  private Account account;
  private List<Invoice> invoices;
  private List<CreditTransaction> transactions;

  public CreditCardVO(CreditCard entity) {
    id = entity.getId();
    user = new UserMinVO(entity.getUser());
    cardBrand = entity.getCardBrand();
    account = entity.getAccount();
    invoices = entity.getInvoices();
    transactions = entity.getTransactions();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserMinVO getUser() {
    return user;
  }

  public void setUser(UserMinVO user) {
    this.user = user;
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
    CreditCardVO that = (CreditCardVO) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
