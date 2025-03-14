package br.com.gritti.app.service;

import br.com.gritti.app.repository.AccountRepository;
import br.com.gritti.app.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServices {
  private final AccountRepository accountRepository;
  private final CreditCardRepository creditCardRepository;


  @Autowired
  public CreditCardServices(AccountRepository accountRepository, CreditCardRepository creditCardRepository) {
    this.accountRepository = accountRepository;
    this.creditCardRepository = creditCardRepository;
  }


}
