package br.com.gritti.app.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

public class UserRequestDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotBlank
  private String username;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String fullName;

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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserRequestDTO that = (UserRequestDTO) o;
    return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(fullName, that.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, email, password, fullName);
  }
}
