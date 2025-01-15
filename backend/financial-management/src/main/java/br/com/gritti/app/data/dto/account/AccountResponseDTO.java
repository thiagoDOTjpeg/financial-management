package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.model.CreditCard;
import br.com.gritti.app.model.DebitCard;

import java.util.List;
import java.util.UUID;

public class AccountResponseDTO {

  private UUID id;
  private UUID userId;
  private String bankName;
  private Double balance;
  private List<CreditCard> creditCard;
  private List<DebitCard> debitCard;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
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
