package br.com.gritti.app.application.dto.bankaccount;

import java.util.UUID;


public class BankAccountCreateDTO  {
  private String bankName;
  private Double balance;
  private UUID userId;

  public BankAccountCreateDTO(String bankName, Double balance, UUID userId) {
    this.bankName = bankName;
    this.balance = balance;
    this.userId = userId;
  }

  public BankAccountCreateDTO() {
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

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
