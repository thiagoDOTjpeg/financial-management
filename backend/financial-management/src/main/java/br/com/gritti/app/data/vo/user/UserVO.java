package br.com.gritti.app.data.vo.user;

import br.com.gritti.app.model.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class UserVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String username;
  private String email;
  private String fullName;
  private Boolean accountNonExpired;
  private Boolean accountNonLocked;
  private Boolean credentialsNonExpired;
  private Boolean accountStatus;
  private Date lastLogin;

  public UserVO() {
  }

  public UserVO(User entity) {
    id = entity.getId();
    username = entity.getUsername();
    email = entity.getEmail();
    fullName = entity.getFullName();
    accountNonExpired = entity.getAccountNonExpired();
    accountNonLocked = entity.getAccountNonLocked();
    credentialsNonExpired = entity.getCredentialsNonExpired();
    accountStatus = entity.getAccountStatus();
    lastLogin = entity.getLastLogin();
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

  public Boolean getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public Boolean getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public Boolean getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public Boolean getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(Boolean accountStatus) {
    this.accountStatus = accountStatus;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserVO userVO = (UserVO) o;
    return Objects.equals(id, userVO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
