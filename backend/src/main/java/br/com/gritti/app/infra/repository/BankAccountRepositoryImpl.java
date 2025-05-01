package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.repository.BankAccountRepository;
import br.com.gritti.app.infra.persistence.JpaBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {

  private final JpaBankAccountRepository jpaBankAccountRepository;

  @Autowired
  public BankAccountRepositoryImpl(JpaBankAccountRepository jpaBankAccountRepository) {
    this.jpaBankAccountRepository = jpaBankAccountRepository;
  }

  @Override
  public Page<BankAccount> findAll(Pageable pageable) {
    return jpaBankAccountRepository.findAll(pageable);
  }

  @Override
  public Optional<BankAccount> findById(UUID id) {
    return jpaBankAccountRepository.findById(id);
  }

  @Override
  public void save(BankAccount account) {
    jpaBankAccountRepository.save(account);
  }

  @Override
  public void delete(BankAccount account) {
    jpaBankAccountRepository.delete(account);
  }
}
