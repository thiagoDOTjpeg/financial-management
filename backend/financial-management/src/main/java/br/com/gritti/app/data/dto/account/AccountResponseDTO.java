package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.data.vo.UserVO;
import br.com.gritti.app.model.CreditCard;
import br.com.gritti.app.model.DebitCard;

import java.util.List;
import java.util.UUID;

public class AccountResponseDTO {

  private UUID id;
  private UserVO user;
  private String bankName;
  private Double balance;
  private List<CreditCard> creditCard;
  private List<DebitCard> debitCard;

  public AccountResponseDTO(UUID id, UserVO user, String bankName, Double balance) {
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

  public UserVO getUser() {
    return user;
  }

  public void setUser(UserVO user) {
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

  public List<CreditCard> getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(List<CreditCard> creditCard) {
    this.creditCard = creditCard;
  }

  public List<DebitCard> getDebitCard() {
    return debitCard;
  }

  public void setDebitCard(List<DebitCard> debitCard) {
    this.debitCard = debitCard;
  }
}
