package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.service.AuthApplicationService;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  public final AuthApplicationService authApplicationService;

  @Autowired
  public AuthController(AuthApplicationService authApplicationService) {
    this.authApplicationService = authApplicationService;
  }

  @PostMapping("/signin")
  public ResponseEntity<Token> signin(@RequestBody AccountCredentials data) {
    Token token = authApplicationService.signin(data);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/refresh")
  public ResponseEntity<Token> refreshToken(@RequestHeader("Authorization") String refreshToken) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Token token = authApplicationService.refreshToken(username, refreshToken);
    return ResponseEntity.ok(token);
  }
}
