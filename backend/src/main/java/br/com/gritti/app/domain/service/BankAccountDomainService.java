package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.infra.repository.BankAccountRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankAccountDomainService {
  private final Logger log = org.slf4j.LoggerFactory.getLogger(BankAccountDomainService.class);

  private final BankAccountRepositoryImpl bankAccountRepositoryImpl;

  @Autowired
  public BankAccountDomainService(BankAccountRepositoryImpl bankAccountRepositoryImpl) {
    this.bankAccountRepositoryImpl = bankAccountRepositoryImpl;
  }

  public Page<BankAccount> getAccounts(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all bank accounts from the repository");

    return bankAccountRepositoryImpl.findAll(pageable);
  }

  public BankAccount getAccountById(UUID id) {
    log.info("DOMAIN: Request received from application and getting bank account by id from the repository");

    return bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
  }

  public BankAccount createAccount(BankAccount account) {
    log.info("DOMAIN: Request received from application and creating a new bank account from the repository");
    bankAccountRepositoryImpl.save(account);
    return account;
  }

  public BankAccount updateAccount(UUID id, BankAccount account) {
    log.info("DOMAIN: Request received from application and updating the bank account with id {}", account.getId());
    BankAccount entity = bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
    bankAccountRepositoryImpl.save(account);
    return entity;
  }

  public void deleteAccount(UUID id) {
    log.info("DOMAIN: Request received from application and deleting the bank account with id {}", id);
    BankAccount account = bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
    bankAccountRepositoryImpl.delete(account);
  }
}
