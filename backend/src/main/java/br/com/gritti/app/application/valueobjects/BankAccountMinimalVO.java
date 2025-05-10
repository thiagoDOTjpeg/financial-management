package br.com.gritti.app.application.valueobjects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

public class BankAccountMinimalVO extends RepresentationModel<BankAccountMinimalVO> {
  private UUID id;
  private String bankName;
  private Double balance;
  private UserMinimalVO user;

  public BankAccountMinimalVO() {
  }

  public BankAccountMinimalVO(UUID id, String bankName, Double balance, UserMinimalVO user) {
    this.id = id;
    this.bankName = bankName;
    this.balance = balance;
    this.user = user;
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

  public UserMinimalVO getUser() {
    return user;
  }

  public void setUser(UserMinimalVO user) {
    this.user = user;
  }
}
