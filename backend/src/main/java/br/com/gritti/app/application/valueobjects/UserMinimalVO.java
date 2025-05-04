package br.com.gritti.app.application.valueobjects;

import br.com.gritti.app.domain.enums.AccountStatus;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

public class UserMinimalVO extends RepresentationModel<UserMinimalVO> {
  private UUID id;
  private String username;
  private String email;
  private Date lastLogin;
  private AccountStatus accountStatus;

  public UserMinimalVO() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public AccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }
}
