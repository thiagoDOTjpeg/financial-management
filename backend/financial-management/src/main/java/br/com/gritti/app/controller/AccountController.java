package br.com.gritti.app.controller;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.service.AccountServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account Endpoint")
@RestController
@RequestMapping("/v1/account")
public class AccountController {

  private final AccountServices service;

  @Autowired
  public AccountController(AccountServices service) {
    this.service = service;
  }

  @Operation(summary = "Creates a account")
  @PostMapping
  public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO dto) throws Exception {
    AccountResponseDTO response = service.createAccount(dto);
    return ResponseEntity.status(201).body(response);
  }

  @Operation(summary = "Gets all accounts")
  @GetMapping
  public ResponseEntity<List<Account>> getAllAccounts() {
    List<Account> response = service.getAllAccounts();
    return ResponseEntity.status(200).body(response);
  }

}
