package br.com.gritti.app.application.valueobjects;

import br.com.gritti.app.domain.enums.AccountStatus;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

public class UserMinimalVO extends RepresentationModel<UserMinimalVO> {
  private UUID id;
  private String username;

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
}
