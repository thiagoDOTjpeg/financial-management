package br.com.gritti.app.repository;

import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByInvoice(Invoice invoice);

}
