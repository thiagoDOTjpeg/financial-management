package br.com.gritti.app.repository;

import br.com.gritti.app.model.DebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, UUID> {
}
