package br.com.gritti.app.data.vo.account;

import br.com.gritti.app.data.vo.credit_card.CreditCardMinVO;
import br.com.gritti.app.data.vo.debit_card.DebitCardMinVO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.DebitCard;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private UserMinVO user;
  private String bankName;
  private BigDecimal balance;
  private List<CreditCardMinVO> creditCards;
  private List<DebitCardMinVO> debitCards;

  public AccountVO(Account entity) {
    id = entity.getId();
    user = new UserMinVO(entity.getUser());
    bankName = entity.getBankName();
    balance = entity.getBalance();
    creditCards = entity.getCreditCards().stream().map(CreditCardMinVO::new).collect(Collectors.toList());
    debitCards = entity.getDebitCards().stream().map(DebitCardMinVO::new).collect(Collectors.toList());
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

  public List<CreditCardMinVO> getCreditCards() {
    return creditCards;
  }

  public void setCreditCards(List<CreditCardMinVO> creditCards) {
    this.creditCards = creditCards;
  }

  public List<DebitCardMinVO> getDebitCards() {
    return debitCards;
  }

  public void setDebitCards(List<DebitCardMinVO> debitCards) {
    this.debitCards = debitCards;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AccountVO accountVO = (AccountVO) o;
    return Objects.equals(id, accountVO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
