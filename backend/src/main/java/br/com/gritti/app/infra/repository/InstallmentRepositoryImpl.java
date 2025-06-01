package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.Installment;
import br.com.gritti.app.domain.repository.InstallmentRepository;
import br.com.gritti.app.infra.persistence.JpaInstallmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class InstallmentRepositoryImpl implements InstallmentRepository {

  private final JpaInstallmentRepository jpaInstallmentRepository;
  private final Logger log = LoggerFactory.getLogger(InstallmentRepositoryImpl.class);

  @Autowired
  public InstallmentRepositoryImpl(JpaInstallmentRepository jpaInstallmentRepository) {
    this.jpaInstallmentRepository = jpaInstallmentRepository;
  }

  @Override
  public Page<Installment> findAll(Pageable pageable) {
    log.info("REPO: Request received from domain and finding all installments from the database");
    return jpaInstallmentRepository.findAll(pageable);
  }

  @Override
  public Optional<Installment> findById(UUID id) {
    log.info("REPO: Request received from domain and finding installment with id {} from the database", id);
    return jpaInstallmentRepository.findById(id);
  }

  @Override
  public void save(Installment installment) {
    log.info("REPO: Request received from domain and saving installment in the database");
    jpaInstallmentRepository.save(installment);
  }

  @Override
  public void delete(Installment installment) {
    log.info("REPO: Request received from domain and deleting installment from the database");
    jpaInstallmentRepository.delete(installment);
  }
}
