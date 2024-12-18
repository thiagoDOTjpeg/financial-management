package br.com.gritti.app.controllers;

import br.com.gritti.app.models.Account;
import br.com.gritti.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @GetMapping
  public List<Account> listAllAccounts() {
    return accountService.listAllAccounts();
  }


  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account createAccount(@RequestBody Account account) {
    return accountService.createAccount(account);
  }

}
