package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.service.AuthApplicationService;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Operações relacionadas a autenticação")
public class AuthController {

  public final AuthApplicationService authApplicationService;

  @Autowired
  public AuthController(AuthApplicationService authApplicationService) {
    this.authApplicationService = authApplicationService;
  }

  @PostMapping("/signin")
  @Operation(summary = "Obter o Token de autenticação", description = "Retorna o Access Token e o Refresh Token")
  public ResponseEntity<Token> signin(@RequestBody AccountCredentials data) {
    Token token = authApplicationService.signin(data);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Renovar o token de autenticação", description = "Renova o Access Token através do Refresh Token")
  public ResponseEntity<Token> refreshToken(@RequestHeader("Authorization") String refreshToken) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Token token = authApplicationService.refreshToken(username, refreshToken);
    return ResponseEntity.ok(token);
  }
}
