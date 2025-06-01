package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.Installment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface InstallmentRepository {
  Page<Installment> findAll(Pageable pageable);
  Optional<Installment> findById(UUID id);
  void save(Installment installment);
  void delete(Installment installment);
}