package br.com.gritti.app.data.security;

import java.io.Serializable;
import java.util.Date;

public class TokenVO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName;
  private Boolean authenticated;
  private Date created;
  private Date expiration;
  private String accessToken;
  private String refreshToken;

  public TokenVO(String userName, Boolean authenticated, Date created, Date expiration, String accessToken, String refreshToken) {
    this.userName = userName;
    this.authenticated = authenticated;
    this.created = created;
    this.expiration = expiration;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public TokenVO() {
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }


}
