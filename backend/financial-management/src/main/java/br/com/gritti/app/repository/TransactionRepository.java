package br.com.gritti.app.repository;

import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT" +
            " transaction.id, " +
            " transaction.id_account, " +
            " transaction.id_invoice, " +
            " transaction.name_transaction, " +
            "transaction.value, " +
            "transaction.timestamp, " +
            "transaction.description " +
            "FROM " +
            "transaction " +
            "INNER JOIN " +
            "invoice " +
            "ON " +
            "transaction.id_invoice=invoice.id " +
            "WHERE " +
            "transaction.id_invoice = ?1", nativeQuery = true)
    List<Transaction> findAllById_invoice(UUID id_invoice);

}
