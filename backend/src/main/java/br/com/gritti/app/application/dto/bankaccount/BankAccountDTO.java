package br.com.gritti.app.application.dto.bankaccount;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccountDTO extends RepresentationModel<BankAccountDTO> {
  private UUID id;
  private String bankName;
  private Double balance;

  public BankAccountDTO(UUID id, String bankName, Double balance) {
    this.id = id;
    this.bankName = bankName;
    this.balance = balance;
  }

  public BankAccountDTO() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
