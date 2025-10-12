package br.com.gritti.shared.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
  @Size(min = 3, max = 100, message = "Username deve ter entre 3 e 100 caracteres")
  private String username;

  @Email(message = "Email inválido")
  private String email;

  @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
  private String password;

  private String fullName;

  public UpdateUserRequest() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
