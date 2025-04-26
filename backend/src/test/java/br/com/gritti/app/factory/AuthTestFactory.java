package br.com.gritti.app.factory;

import br.com.gritti.app.domain.valueobject.AccountCredentials;

public class AuthTestFactory {
  public static AccountCredentials createCredentials() {
    return new AccountCredentials("test1", "test123");
  }
}
