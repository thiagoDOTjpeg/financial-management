package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.installment.InstallmentCreateDTO;
import br.com.gritti.app.application.mapper.InstallmentMapper;
import br.com.gritti.app.domain.model.Installment;
import br.com.gritti.app.domain.service.InstallmentDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallmentApplicationService {
  private final Logger log = LoggerFactory.getLogger(InstallmentApplicationService.class);
  private final InstallmentDomainService installmentDomainService;
  private final InstallmentMapper installmentMapper;

  @Autowired
  public InstallmentApplicationService(InstallmentDomainService installmentDomainService, InstallmentMapper installmentMapper ) {
    this.installmentMapper = installmentMapper;
    this.installmentDomainService = installmentDomainService;
  }

  public Installment createInstallment(InstallmentCreateDTO installmentCreateDTO) {
    log.info("APPLICATION: Request received from controller and passing to domain to create a new installment");
    Installment installment = installmentMapper.installmentCreateDTOtoInstallment(installmentCreateDTO);
    return installmentDomainService.createInstallment(installment);
  }
}
