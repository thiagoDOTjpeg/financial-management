package br.com.gritti.app.data.vo.account;

import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.User;

import java.util.Objects;
import java.util.UUID;

public class AccountMinVO {

  private UUID id;
  private User user;
  private String bankName;
  private Double balance;

  public AccountMinVO(Account entity) {
    id = entity.getId();
    user = entity.getUser();
    bankName = entity.getBankName();
    balance = entity.getBalance();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AccountMinVO that = (AccountMinVO) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
