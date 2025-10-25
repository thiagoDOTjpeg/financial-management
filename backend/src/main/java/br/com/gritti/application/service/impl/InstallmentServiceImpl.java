package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.InstallmentService;
import br.com.gritti.domain.repository.InstallmentRepository;
import br.com.gritti.shared.dto.response.CancelInstallmentResponse;
import br.com.gritti.shared.dto.response.InstallmentResponse;
import br.com.gritti.shared.mapper.InstallmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InstallmentServiceImpl implements InstallmentService {


  private final InstallmentRepository installmentRepository;

  @Autowired
  public InstallmentServiceImpl(InstallmentRepository installmentRepository) {
    this.installmentRepository = installmentRepository;
  }

  @Override
  public Optional<InstallmentResponse> getById(UUID id) {
    return Optional.empty();
  }

  @Override
  public Page<InstallmentResponse> getAll(Pageable pageable) {
    return installmentRepository.findAll(pageable).map(InstallmentMapper::toResponse);
  }

  @Override
  public void cancelInstallment(UUID id) {
    installmentRepository.cancelInstallment(id);
  }

  @Override
  public CancelInstallmentResponse cancelInstallmentManul(UUID id, UUID useId) {
    return installmentRepository.cancelInstallmentManual(id, useId);
  }
}
