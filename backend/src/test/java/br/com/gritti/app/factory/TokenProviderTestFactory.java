package br.com.gritti.app.factory;

import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.infra.security.jwt.TokenProvider;

import java.util.Date;
import java.util.List;

public class TokenProviderTestFactory {
  private static long validityInMilliseconds = 3600000;
  private static final TokenProvider tokenProvider = new TokenProvider();
  private static String username = "test1";
  private static List<String> roles = List.of("ROLE_ADMIN");

  public static Token createToken() {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    return tokenProvider.createToken(username, roles);
  }

  public static Token refreshToken(String refreshToken) {
    return tokenProvider.refreshToken(refreshToken);
  }
}
