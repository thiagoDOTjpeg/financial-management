package br.com.gritti.app.application.dto.minimal;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class BankAccountMinimalDTO extends RepresentationModel<BankAccountMinimalDTO> {
  private UUID id;
  private String bankName;
  private UserMinimalDTO user;

  public BankAccountMinimalDTO() {
  }

  public BankAccountMinimalDTO(UUID id, String bankName, UserMinimalDTO user) {
    this.id = id;
    this.bankName = bankName;
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

  public UserMinimalDTO getUser() {
    return user;
  }

  public void setUser(UserMinimalDTO user) {
    this.user = user;
  }
}
