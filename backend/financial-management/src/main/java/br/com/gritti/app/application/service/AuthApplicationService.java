package br.com.gritti.app.application.service;

import br.com.gritti.app.domain.service.AuthDomainService;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {
  private final AuthDomainService authDomainService;

  @Autowired
  public AuthApplicationService(AuthDomainService authDomainService) {
    this.authDomainService = authDomainService;
  }

  public Token signin(AccountCredentials data) {
    if(data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank()) {
      throw new BadCredentialsException("Invalid credentials");
    }
    return authDomainService.signin(data);
  }

  public Token refreshToken(String username, String refreshToken) {
    if(refreshToken == null || refreshToken.isEmpty() || username == null || username.isEmpty()) {
      throw new BadCredentialsException("Invalid client request!");
    }
   Token token = authDomainService.refreshToken(username, refreshToken);
    if(token == null) {
      throw new BadCredentialsException("Invalid client request!");
    }
    return token;
  }
}
