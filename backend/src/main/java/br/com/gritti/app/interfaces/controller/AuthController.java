package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.service.AuthApplicationService;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.interfaces.controller.docs.AuthControllerDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Operações relacionadas a autenticação")
public class AuthController implements AuthControllerDocs {

  public final AuthApplicationService authApplicationService;

  @Autowired
  public AuthController(AuthApplicationService authApplicationService) {
    this.authApplicationService = authApplicationService;
  }

  @PostMapping("/signin")
  @Override
  public ResponseEntity<Token> signin(@RequestBody AccountCredentials data) {
    if(data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank()) {
      throw new BadCredentialsException("Invalid credentials");
    }
    Token token = authApplicationService.signin(data);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/refresh")
  @Override
  public ResponseEntity<Token> refreshToken(@RequestHeader("Authorization") String refreshToken) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if(refreshToken == null || refreshToken.isEmpty() || username == null || username.isEmpty()) {
      throw new BadCredentialsException("Invalid client request!");
    }
    Token token = authApplicationService.refreshToken(username, refreshToken);
    return ResponseEntity.ok(token);
  }
}
