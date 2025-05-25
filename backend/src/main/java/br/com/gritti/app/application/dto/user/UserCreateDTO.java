package br.com.gritti.app.application.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateDTO {
  
  @NotBlank(message = "Username Cannot Be Blank")
  private String username;

  @NotBlank(message = "Full Name Cannot Be Blank")
  private String fullName;

  @NotBlank(message = "Email Cannot Be Blank")
  @Email(message = "Invalid Email")
  private String email;

  @NotBlank(message = "Password Cannot Be Blank")
  private String password;

  public UserCreateDTO(String username, String fullName, String email, String password) {
    this.username = username;
    this.fullName = fullName;
    this.email = email;
    this.password = password;
  }

  public UserCreateDTO() {
  }

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
}
