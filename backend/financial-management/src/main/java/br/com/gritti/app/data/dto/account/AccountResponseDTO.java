package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.data.vo.credit_card.CreditCardMinVO;
import br.com.gritti.app.data.vo.debit_card.DebitCardMinVO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.model.Account;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class AccountResponseDTO {

  private UUID id;
  private UserMinVO user;
  private String bankName;
  private BigDecimal balance;
  private List<CreditCardMinVO> creditCard;
  private List<DebitCardMinVO> debitCard;

  public AccountResponseDTO(Account entity) {
    id = entity.getId();
    user = new UserMinVO(entity.getUser());
    bankName = entity.getBankName();
    balance = entity.getBalance();
    creditCard = Optional.ofNullable(entity.getCreditCards()).orElseGet(Collections::emptyList).stream().map(CreditCardMinVO::new).collect(Collectors.toList());
    debitCard = Optional.ofNullable(entity.getDebitCards()).orElseGet(Collections::emptyList).stream().map(DebitCardMinVO::new).collect(Collectors.toList());
  }

  public AccountResponseDTO(UUID id, UserMinVO user, String bankName, BigDecimal balance) {
    this.id = id;
    this.user = user;
    this.bankName = bankName;
    this.balance = balance;
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


  public List<CreditCardMinVO> getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(List<CreditCardMinVO> creditCard) {
    this.creditCard = creditCard;
  }

  public List<DebitCardMinVO> getDebitCard() {
    return debitCard;
  }

  public void setDebitCard(List<DebitCardMinVO> debitCard) {
    this.debitCard = debitCard;
  }
}
