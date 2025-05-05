package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.repository.BankAccountRepository;
import br.com.gritti.app.infra.persistence.JpaBankAccountRepository;
import br.com.gritti.app.infra.persistence.JpaCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
  private final Logger log = LoggerFactory.getLogger(BankAccountRepositoryImpl.class);
  private final JpaBankAccountRepository jpaBankAccountRepository;
  private final JpaCardRepository jpaCardRepository;

  @Autowired
  public BankAccountRepositoryImpl(JpaBankAccountRepository jpaBankAccountRepository, JpaCardRepository jpaCardRepository) {
    this.jpaBankAccountRepository = jpaBankAccountRepository;
    this.jpaCardRepository = jpaCardRepository;
  }

  @Override
  public Page<BankAccount> findAll(Pageable pageable) {
    log.info("REPO: Request received from domain and finding all bank accounts from the database");
    return jpaBankAccountRepository.findAll(pageable);
  }

  @Override
  public Page<BankAccount> findAllByUsername(Pageable pageable, String username) {
    return jpaBankAccountRepository.findAllByUsername(username, pageable);
  }

  @Override
  public Optional<BankAccount> findById(UUID id) {
    log.info("REPO: Request received from domain and finding bank account with id {} from the database", id);
    return jpaBankAccountRepository.findById(id);
  }

  @Override
  public void save(BankAccount account) {
    log.info("REPO: Request received from domain and saving bank account in the database");
    jpaBankAccountRepository.save(account);
  }

  @Override
  public void delete(BankAccount account) {
    log.info("REPO: Request received from domain and deleting bank account from the database");
    jpaBankAccountRepository.delete(account);
  }
}
