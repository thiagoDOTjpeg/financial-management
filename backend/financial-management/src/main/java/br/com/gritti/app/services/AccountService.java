package br.com.gritti.app.services;

import br.com.gritti.app.models.Account;
import br.com.gritti.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
      this.accountRepository = accountRepository;
    }

    public List<Account> listAllAccounts() {
      return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
      return accountRepository.save(account);
    }
}
