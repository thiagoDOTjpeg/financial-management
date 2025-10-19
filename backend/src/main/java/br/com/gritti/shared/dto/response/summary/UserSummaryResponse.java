package br.com.gritti.shared.dto.response.summary;

import java.util.UUID;

public class UserSummaryResponse {
  private UUID id;
  private String username;
  private String email;
  private String fullName;

  public UserSummaryResponse() {
  }

  public UserSummaryResponse(UUID id, String username, String email, String fullName) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.fullName = fullName;
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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
