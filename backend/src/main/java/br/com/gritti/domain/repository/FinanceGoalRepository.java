package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinanceGoalRepository extends JpaRepository<FinancialGoal, UUID> {
}
