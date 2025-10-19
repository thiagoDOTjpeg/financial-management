package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.RecurringTransaction;
import br.com.gritti.shared.dto.response.summary.RecurringTransactionSummaryResponse;

public class RecurringTransactionMapper {

  public static RecurringTransactionSummaryResponse toSummaryResponse(RecurringTransaction recurringTransaction) {
    RecurringTransactionSummaryResponse response = new RecurringTransactionSummaryResponse();
    response.setId(recurringTransaction.getId());
    response.setDescription(recurringTransaction.getDescription());
    response.setAmount(recurringTransaction.getAmount());
    response.setActive(recurringTransaction.getActive());
    response.setStartDate(recurringTransaction.getStartDate());
    response.setEndDate(recurringTransaction.getEndDate());
    response.setFrequency(recurringTransaction.getFrequency());
    response.setPaymentType(recurringTransaction.getPaymentType());
    response.setDayOfMonth(recurringTransaction.getDayOfMonth());
    response.setDayOfWeek(recurringTransaction.getDayOfWeek());
    return response;
  }
}
