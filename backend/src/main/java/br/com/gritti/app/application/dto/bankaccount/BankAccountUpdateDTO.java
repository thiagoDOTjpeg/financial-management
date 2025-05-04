package br.com.gritti.app.application.dto.bankaccount;

public class BankAccountUpdateDTO {
  private String bankName;
  private Double balance;

  public BankAccountUpdateDTO() {
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
