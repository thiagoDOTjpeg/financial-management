package br.com.gritti.app.domain.service;

import br.com.gritti.app.application.mapper.BankAccountMapper;
import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.infra.repository.BankAccountRepositoryImpl;
import br.com.gritti.app.shared.exceptions.InvalidBalanceException;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankAccountDomainService {
  private final Logger log = LoggerFactory.getLogger(BankAccountDomainService.class);

  private final BankAccountRepositoryImpl bankAccountRepositoryImpl;
  private final BankAccountMapper bankAccountMapper;

  @Autowired
  public BankAccountDomainService(BankAccountRepositoryImpl bankAccountRepositoryImpl, BankAccountMapper bankAccountMapper) {
    this.bankAccountRepositoryImpl = bankAccountRepositoryImpl;
    this.bankAccountMapper = bankAccountMapper;
  }

  public Page<BankAccount> getAccounts(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all bank accounts from the repository");
    return bankAccountRepositoryImpl.findAll(pageable);
  }

  public Page<BankAccount> getAccounts(Pageable pageable, String username) {
    log.info("DOMAIN: Request received from application and getting all bank accounts from the repository and filtering by username: {}", username);
    return bankAccountRepositoryImpl.findAllByUsername(pageable, username);
  }

  public BankAccount getAccountById(UUID id) {
    log.info("DOMAIN: Request received from application and getting bank account by id from the repository");
    return bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
  }

  public void createAccount(BankAccount account) {
    log.info("DOMAIN: Request received from application and creating a new bank account from the repository");
    if(account.getBalance() == null) account.setBalance(0.0);
    if(account.getBalance() < 0) throw new InvalidBalanceException("Invalid balance for bank account");
    bankAccountRepositoryImpl.save(account);
  }

  public BankAccount updateAccount(UUID id, BankAccount account) {
    log.info("DOMAIN: Request received from application and updating the bank account with id {}", account.getId());
    if(account.getBalance() != null && account.getBalance() < 0) throw new InvalidBalanceException("Invalid balance for bank account");
    BankAccount entity = bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
    bankAccountMapper.updateBankAccount(account, entity);
    bankAccountRepositoryImpl.save(entity);
    return entity;
  }

  public void deleteAccount(UUID id) {
    log.info("DOMAIN: Request received from application and deleting the bank account with id {}", id);
    BankAccount account = bankAccountRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank account with id " + id + " not found"));
    bankAccountRepositoryImpl.delete(account);
  }
}
