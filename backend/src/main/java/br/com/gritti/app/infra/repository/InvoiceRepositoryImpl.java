package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Invoice;
import br.com.gritti.app.domain.repository.InvoiceRepository;
import br.com.gritti.app.infra.persistence.JpaInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
  private final Logger log = LoggerFactory.getLogger(InvoiceRepositoryImpl.class);
  private final JpaInvoiceRepository jpaInvoiceRepository;

  @Autowired
  public InvoiceRepositoryImpl(JpaInvoiceRepository jpaInvoiceRepository) {
    this.jpaInvoiceRepository = jpaInvoiceRepository;
  }

  @Override
  public Page<Invoice> findAll(Pageable pageable) {
    log.info("REPO: Request received from domain and finding all invoices from the database.");
    return jpaInvoiceRepository.findAll(pageable);
  }

  @Override
  public Optional<Invoice> findById(UUID id) {
    log.info("REPO: Request received from domain and finding invoice with id {} from the database.", id);
    return jpaInvoiceRepository.findById(id);
  }

  @Override
  public Optional<Invoice> findByBillingMonthAndCardAndStatus(Date billingMonth, Card card, InvoiceStatus status) {
    return jpaInvoiceRepository.findByBillingMonthAndCardAndStatus(billingMonth, card, status);
  }

  @Override
  public void save(Invoice invoice) {
    log.info("REPO: Request received from domain and saving invoice in the database.");
    jpaInvoiceRepository.save(invoice);
  }

  @Override
  public void delete(Invoice invoice) {
    log.info("REPO: Request received from domain and deleting invoice from the database.");
    jpaInvoiceRepository.delete(invoice);
  }
}
