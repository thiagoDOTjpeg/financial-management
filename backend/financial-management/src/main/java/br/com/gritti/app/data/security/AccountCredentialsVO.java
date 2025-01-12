package br.com.gritti.app.data.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName;
  private String password;

  public AccountCredentialsVO(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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
    AccountCredentialsVO that = (AccountCredentialsVO) o;
    return Objects.equals(userName, that.userName) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, password);
  }
}
