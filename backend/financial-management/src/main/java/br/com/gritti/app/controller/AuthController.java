package br.com.gritti.app.controller;

import br.com.gritti.app.data.security.AccountCredentialsVO;
import br.com.gritti.app.service.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthServices authServices;

  @SuppressWarnings("rawtypes")
  @Operation(summary = "Authenticates a user and returns a token")
  @PostMapping(value = "/signin")
  public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
    if (checkIfParamsIsNotNull(data)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    }

    var token = authServices.signin(data);
    if(token == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    }
    return token;
  }

  @SuppressWarnings("rawtypes")
  @Operation(summary = "Refresh the expired JWT Token and return a new valid accessToken")
  @PutMapping(value = "/refresh/{username}")
  public ResponseEntity refreshToken(@PathVariable String username, @RequestHeader("Authorization") String refreshToken) {
    if(refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    }
    var token = authServices.refreshToken(username, refreshToken);
    if(token == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    }
    return token;
  }

  private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
    return data == null || data.getUserName() == null || data.getUserName().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
  }
}
