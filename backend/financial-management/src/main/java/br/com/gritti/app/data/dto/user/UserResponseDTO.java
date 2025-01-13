package br.com.gritti.app.data.dto.user;

import java.util.UUID;

public class UserResponseDTO {

  private UUID id;
  private String username;
  private String message;

  public UserResponseDTO(UUID id, String username, String message) {
    this.id = id;
    this.username = username;
    this.message = message;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
