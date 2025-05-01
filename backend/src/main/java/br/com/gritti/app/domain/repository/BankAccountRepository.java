package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
  Page<BankAccount> findAll(Pageable pageable);
  Optional<BankAccount> findById(UUID id);
  void save(BankAccount account);
  void delete(BankAccount account);
}
