package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Installment;
import br.com.gritti.shared.dto.response.InstallmentResponse;
import br.com.gritti.shared.dto.response.summary.InstallmentSummaryResponse;

public class InstallmentMapper {

  public static InstallmentResponse toResponse(Installment installment) {
    InstallmentResponse response = new InstallmentResponse();
    response.setId(installment.getId());
    response.setCard(installment.getCard());
    response.setDescription(installment.getDescription());
    response.setTotalAmount(installment.getTotalAmount());
    response.setTotalInstallments(installment.getTotalInstallments());
    response.setInstallmentValue(installment.getInstallmentValue());
    response.setFirstDueDate(installment.getFirstDueDate());
    response.setTransactions(installment.getTransactions());
    return response;
  }

  public static InstallmentSummaryResponse toSummaryResponse(Installment installment) {
    InstallmentSummaryResponse response = new InstallmentSummaryResponse();
    response.setId(installment.getId());
    response.setDescription(installment.getDescription());
    response.setInstallmentValue(installment.getInstallmentValue());
    response.setFirstDueDate(installment.getFirstDueDate());
    response.setTotalInstallments(installment.getTotalInstallments());
    response.setTotalAmount(installment.getTotalAmount());
    return response;
  }
}
