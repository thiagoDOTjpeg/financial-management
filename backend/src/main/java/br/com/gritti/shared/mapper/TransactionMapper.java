package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Transaction;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.request.transaction.UpdateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;

public class TransactionMapper {
  public TransactionMapper() {
  }

  public static Transaction toEntity(CreateTransactionRequest request) {
    return new Transaction.Builder()
            .description(request.description())
            .amount(request.amount())
            .transactionDate(request.transactionDate())
            .paymentType(request.paymentType())
            .installmentNumber(request.installmentNumber())
            .notes(request.notes())
            .build();
  }

  public static TransactionResponse toResponse(Transaction transaction) {
    TransactionResponse response = new TransactionResponse();
    response.setCategory(response.getCategory());
    response.setDescription(response.getDescription());
    response.setAmount(response.getAmount());
    response.setTransactionDate(response.getTransactionDate());
    response.setPaymentType(response.getPaymentType());
    response.setBankAccount(response.getBankAccount());
    response.setInvoice(response.getInvoice());
    response.setInstallment(response.getInstallment());
    response.setRecurringTransaction(response.getRecurringTransaction());
    response.setInstallmentNumber(response.getInstallmentNumber());
    response.setNotes(response.getNotes());
    return response;
  }
}
