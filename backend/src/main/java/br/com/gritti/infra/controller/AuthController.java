package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.AuthServiceImpl;
import br.com.gritti.domain.vo.AccountCredentials;
import br.com.gritti.domain.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthServiceImpl authServiceImpl;

  @Autowired
  public AuthController(AuthServiceImpl authServiceImpl) {
    this.authServiceImpl = authServiceImpl;
  }

  @PostMapping("/signin")
  public ResponseEntity<Token> signin(@RequestBody AccountCredentials data) {
    if(data == null || data.username() == null || data.username().isBlank() || data.password() == null || data.password().isBlank()) {
      throw new BadCredentialsException("Invalid credentials");
    }
    Token token = authServiceImpl.signin(data);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/refresh")
  public ResponseEntity<Token> refreshToken(@RequestHeader("Authorization") String refreshToken) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if(refreshToken == null || refreshToken.isEmpty() || username == null || username.isEmpty()) {
      throw new BadCredentialsException("Invalid client request!");
    }
    Token token = authServiceImpl.refreshToken(username, refreshToken);
    return ResponseEntity.ok(token);
  }
}
