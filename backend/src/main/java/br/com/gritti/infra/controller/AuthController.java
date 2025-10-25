package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.AuthServiceImpl;
import br.com.gritti.domain.vo.AccountCredentials;
import br.com.gritti.domain.vo.Token;
import br.com.gritti.infra.controller.contract.AuthApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController implements AuthApi {
  private final AuthServiceImpl authServiceImpl;

  @Autowired
  public AuthController(AuthServiceImpl authServiceImpl) {
    this.authServiceImpl = authServiceImpl;
  }

  @Override
  public ResponseEntity<Token> signin(AccountCredentials data) {
    if(data == null || data.username() == null || data.username().isBlank() || data.password() == null || data.password().isBlank()) {
      throw new BadCredentialsException("Invalid credentials");
    }
    Token token = authServiceImpl.signin(data);
    return ResponseEntity.ok(token);
  }

  @Override
  public ResponseEntity<Token> refreshToken(String refreshToken) {
    if(refreshToken == null || refreshToken.isEmpty()) {
      throw new BadCredentialsException("Invalid client request!");
    }
    Token token = authServiceImpl.refreshToken(refreshToken);
    return ResponseEntity.ok(token);
  }
}
