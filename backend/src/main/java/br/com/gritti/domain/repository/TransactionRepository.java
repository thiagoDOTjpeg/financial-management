package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  @Query("UPDATE Transaction t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.id = :id")
  @Modifying
  void softDelete(UUID id);
}
