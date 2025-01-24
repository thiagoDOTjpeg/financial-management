package br.com.gritti.app.repository;

import br.com.gritti.app.model.CreditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, UUID> {
}
