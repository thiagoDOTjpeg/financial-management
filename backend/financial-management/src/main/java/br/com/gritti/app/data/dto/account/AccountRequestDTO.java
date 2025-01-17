package br.com.gritti.app.data.dto.account;

import br.com.gritti.app.model.Auditable;

import java.util.UUID;

public class AccountRequestDTO extends Auditable {

  private UUID user_id;
  private String bankName;
  private Double balance;


  public UUID getUser_id() {
    return user_id;
  }

  public void setUser_id(UUID user_id) {
    this.user_id = user_id;
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
