package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.RecurringTransaction;
import br.com.gritti.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  @Query("UPDATE Transaction t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.id = :id")
  @Modifying
  void softDelete(UUID id);

  @Override
  @EntityGraph(attributePaths = { "bankAccount", "installment", "invoice", "invoice.card",
          "bankAccount.user", "bankAccount.user.subscription", "category", "recurringTransaction" })
  Page<Transaction> findAll(Pageable pageable);

  Optional<Transaction> findFirstTransactionByRecurringTransactionOrderByCreatedAtAsc(RecurringTransaction recurringTransaction);

  @EntityGraph(attributePaths = { "bankAccount", "installment", "invoice", "invoice.card", "bankAccount.user",
          "bankAccount.user.subscription", "category", "recurringTransaction" })
  Page<Transaction> findAllByDeletedAtIsNull(Pageable pageable);

  Optional<Transaction> findFirstByInstallmentIdOrderByInstallmentNumber(UUID id);
}
