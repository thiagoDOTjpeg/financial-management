package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
  Page<Transaction> findAll(Pageable pageable);
  Page<Transaction> findAllByUsername(Pageable pageable, String username);
  Optional<Transaction> findById(UUID id);
  void save(Transaction transaction);
  void delete(Transaction transaction);
}
