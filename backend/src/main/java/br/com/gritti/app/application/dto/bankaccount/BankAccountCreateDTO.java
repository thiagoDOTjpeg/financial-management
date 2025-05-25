package br.com.gritti.app.application.dto.bankaccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class BankAccountCreateDTO  {
  @NotBlank(message = "Bank name Cannot Be Blank")
  private String bankName;
  @NotNull(message = "Balance Cannot Be Null")
  private Double balance;

  public BankAccountCreateDTO(String bankName, Double balance) {
    this.bankName = bankName;
    this.balance = balance;
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
}
