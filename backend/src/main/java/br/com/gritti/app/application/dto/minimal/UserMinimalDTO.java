package br.com.gritti.app.application.dto.minimal;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class UserMinimalDTO extends RepresentationModel<UserMinimalDTO> {
  private UUID id;
  private String username;

  public UserMinimalDTO() {
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
