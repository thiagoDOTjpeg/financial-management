package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.model.Auditable;

import java.util.UUID;

public class AccountRequestDTO extends Auditable {

  private UUID userId;
  private String bankName;
  private Double balance;

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
}
