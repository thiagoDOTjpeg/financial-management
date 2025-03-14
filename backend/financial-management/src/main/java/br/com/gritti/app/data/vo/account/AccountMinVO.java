package br.com.gritti.app.data.vo.account;

import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.User;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AccountMinVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private UserMinVO user;
  private String bankName;
  private BigDecimal balance;

  public AccountMinVO(Account entity) {
    id = entity.getId();
    user = new UserMinVO(entity.getUser());
    bankName = entity.getBankName();
    balance = entity.getBalance();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserMinVO getUser() { return user; }

  public void setUser(UserMinVO user) { this.user = user; }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
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
