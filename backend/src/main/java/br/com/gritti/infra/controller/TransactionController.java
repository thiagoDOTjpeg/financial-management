package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.TransactionServiceImpl;
import br.com.gritti.domain.model.Transaction;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
  private final TransactionServiceImpl transactionServiceImpl;

  @Autowired
  public TransactionController(TransactionServiceImpl transactionServiceImpl) {
    this.transactionServiceImpl = transactionServiceImpl;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<TransactionResponse>>> getAlTransactions(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          PagedResourcesAssembler<TransactionResponse> pagedResourcesAssembler
  ) {
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "transactionDate"));
    return ResponseEntity.ok(pagedResourcesAssembler.toModel(transactionServiceImpl.getAll(pageable)));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> createTransaction(@AuthenticatedUserId UUID userId, @RequestBody  CreateTransactionRequest request) {
    TransactionResponse response = transactionServiceImpl.createTransaction(userId,  request);
    return ResponseEntity.ok(response);
  }
}
