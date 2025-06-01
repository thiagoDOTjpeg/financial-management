package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.Installment;
import br.com.gritti.app.infra.repository.InstallmentRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallmentDomainService {
  private final Logger log = LoggerFactory.getLogger(InstallmentDomainService.class);
  private final InstallmentRepositoryImpl installmentRepositoryImpl;

  @Autowired
  public InstallmentDomainService(InstallmentRepositoryImpl installmentRepositoryImpl) {
    this.installmentRepositoryImpl = installmentRepositoryImpl;
  }

  public Installment createInstallment(Installment installment) {
    log.info("DOMAIN: Request received from application and saving a new installment from the repository");
    installmentRepositoryImpl.save(installment);
    return installment;
  }
}
