package br.com.gritti.app.application.dto.user;

import br.com.gritti.app.domain.valueobject.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {

  @NotBlank(message = "Username Cannot Be Blank")
  private String username;

  @NotBlank(message = "Full Name Cannot Be Blank")
  private String fullName;

  @NotBlank(message = "Email Cannot Be Blank")
  private Email email;

  @NotBlank(message = "Password Cannot Be Blank")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
