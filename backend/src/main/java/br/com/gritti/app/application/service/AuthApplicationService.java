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

  @Autowired
  private AuthDomainService authDomainService;

  public Token signin(AccountCredentials data) {
    return authDomainService.signin(data);
  }

  public Token refreshToken(String username, String refreshToken) {
    return authDomainService.refreshToken(username, refreshToken);
  }
}
