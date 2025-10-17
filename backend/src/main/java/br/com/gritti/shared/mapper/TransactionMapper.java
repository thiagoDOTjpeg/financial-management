package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Transaction;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
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
    response.setCategory(transaction.getCategory());
    response.setDescription(transaction.getDescription());
    response.setAmount(transaction.getAmount());
    response.setTransactionDate(transaction.getTransactionDate());
    response.setPaymentType(transaction.getPaymentType());
//    response.setBankAccount(BankAccountMapper.toResponse(transaction.getBankAccount()));
    response.setInvoice(InvoiceMapper.toResponse(transaction.getInvoice()));
//    response.setInstallment(transaction.getInstallment());
    response.setRecurringTransaction(transaction.getRecurringTransaction());
    response.setInstallmentNumber(transaction.getInstallmentNumber());
    response.setNotes(transaction.getNotes());
    return response;
  }
}
