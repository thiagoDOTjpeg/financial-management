package br.com.gritti.app.domain.valueobject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class AccountCredentials implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String username;
  private String password;

  public AccountCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AccountCredentials that = (AccountCredentials) o;
    return Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(username);
  }
}
