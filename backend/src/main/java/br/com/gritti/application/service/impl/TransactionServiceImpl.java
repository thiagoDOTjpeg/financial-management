package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.TransactionService;
import br.com.gritti.domain.model.Transaction;
import br.com.gritti.domain.repository.TransactionRepository;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.request.transaction.UpdateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;

  @Autowired
  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public TransactionResponse getById(UUID id) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
    return TransactionMapper.toResponse(transaction);
  }

  @Override
  public Page<TransactionResponse> getAll(Pageable pageable) {
    Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
    return transactionPage.map(TransactionMapper::toResponse);
  }

  @Override
  public TransactionResponse updateTransaction(UUID id, UpdateTransactionRequest request) {
    Transaction response = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
    //TODO
    return null;
  }

  @Override
  public TransactionResponse createTransaction(CreateTransactionRequest request) {
    Transaction transaction = TransactionMapper.toEntity(request);
    return TransactionMapper.toResponse(transactionRepository.save(transaction));
  }

  @Override
  public void softDelete(UUID id) {
    transactionRepository.softDelete(id);
  }
}
