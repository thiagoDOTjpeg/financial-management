package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, UUID> {
  @Query("SELECT i FROM Invoice i WHERE i.billingMonth = :billingMonth AND i.card = :card AND i.status = :status")
  Optional<Invoice> findByBillingMonthAndCardAndStatus(@Param("billingMonth") Date billingMonth, @Param("card") Card card, @Param("status") InvoiceStatus status);
}
