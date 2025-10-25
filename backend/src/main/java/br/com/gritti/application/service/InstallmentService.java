package br.com.gritti.application.service;

import br.com.gritti.shared.dto.response.CancelInstallmentResponse;
import br.com.gritti.shared.dto.response.InstallmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface InstallmentService {
  Optional<InstallmentResponse> getById(UUID id);
  Page<InstallmentResponse> getAll(Pageable pageable);
  void cancelInstallment(UUID id);
  CancelInstallmentResponse cancelInstallmentManul(UUID id, UUID useId);
}
