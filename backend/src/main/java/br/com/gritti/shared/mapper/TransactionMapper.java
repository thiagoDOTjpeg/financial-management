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
            .transactionType(request.transactionType())
            .installmentNumber(request.installments())
            .notes(request.notes())
            .build();

  }

  public static TransactionResponse toResponse(Transaction transaction) {
    if (transaction == null) {
      return null;
    }

    TransactionResponse response = new TransactionResponse();
    response.setId(transaction.getId());
    response.setDescription(transaction.getDescription());
    response.setAmount(transaction.getAmount());
    response.setTransactionDate(transaction.getTransactionDate());
    response.setPaymentType(transaction.getPaymentType());
    response.setTransactionType(transaction.getTransactionType());
    response.setInstallmentNumber(transaction.getInstallmentNumber());
    response.setNotes(transaction.getNotes());

    if(transaction.getRecurringTransaction() != null) {
      response.setRecurringTransaction(RecurringTransactionMapper.toSummaryResponse(transaction.getRecurringTransaction()));
    }

    if(transaction.getCategory() != null) {
      response.setCategory(CategoryMapper.toCategorySummaryResponse(transaction.getCategory()));
    }

    if (transaction.getBankAccount() != null) {
      response.setBankAccount(BankAccountMapper.toResponse(transaction.getBankAccount()));
    }

    if (transaction.getInvoice() != null) {
      response.setInvoice(InvoiceMapper.toResponse(transaction.getInvoice()));
    }

    if (transaction.getInstallment() != null) {
      response.setInstallment(InstallmentMapper.toSummaryResponse(transaction.getInstallment()));
    }

    return response;
  }
}