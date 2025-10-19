package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.GoalTransaction;
import br.com.gritti.domain.model.GoalTransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface GoalTransactionRepository extends JpaRepository<GoalTransaction, GoalTransactionId> {

  List<GoalTransaction> findByIdGoalId(UUID goalId);

  boolean existsByIdTransactionId(UUID transactionId);

  @Query("""
        SELECT gt FROM GoalTransaction gt\s
        JOIN FETCH gt.transaction t\s
        WHERE gt.id.goalId = :goalId\s
        ORDER BY t.transactionDate DESC
   \s""")
  List<GoalTransaction> findByGoalIdWithTransactions(@Param("goalId") UUID goalId);

  @Query("""
        SELECT COALESCE(SUM(t.amount), 0)\s
        FROM GoalTransaction gt\s
        JOIN gt.transaction t\s
        WHERE gt.id.goalId = :goalId\s
        AND t.deletedAt IS NULL
   \s""")
  BigDecimal calculateTotalContributed(@Param("goalId") UUID goalId);

  void deleteByIdGoalId(UUID goalId);

  void deleteByIdGoalIdAndIdTransactionId(UUID goalId, UUID transactionId);
}