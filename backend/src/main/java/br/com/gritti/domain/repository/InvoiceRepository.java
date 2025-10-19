package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
  Optional<Invoice> findByCardIdAndBillingMonth(UUID cardId, LocalDate billingMonth);
}
