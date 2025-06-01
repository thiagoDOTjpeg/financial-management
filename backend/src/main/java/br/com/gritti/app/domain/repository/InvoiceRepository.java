package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository {
  Page<Invoice> findAll(Pageable pageable);
  Optional<Invoice> findById(UUID id);
  Optional<Invoice> findByBillingMonthAndCardAndStatus(Date billingMonth, Card card, InvoiceStatus status);
  void save(Invoice invoice);
  void delete(Invoice invoice);
}
