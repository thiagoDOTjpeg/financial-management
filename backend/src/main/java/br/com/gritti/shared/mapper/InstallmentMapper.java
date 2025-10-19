package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Installment;
import br.com.gritti.shared.dto.response.summary.InstallmentSummaryResponse;

public class InstallmentMapper {
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
