package br.com.gritti.application.service;

import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.request.transaction.UpdateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
  TransactionResponse getById(UUID id);
  Page<TransactionResponse> getAll(Pageable pageable);
  TransactionResponse updateTransaction(UUID id, UpdateTransactionRequest request);
  TransactionResponse createTransaction(UUID userId, CreateTransactionRequest request);
  void softDelete(UUID id);
}
