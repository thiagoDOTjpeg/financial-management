package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.application.service.TransactionApplicationService;
import br.com.gritti.app.domain.model.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
@Tag(name = "Transaction", description = "Operações relacionado a Transações")
public class TransactionController {
  private final Logger log = LoggerFactory.getLogger(TransactionController.class);
  private final TransactionApplicationService transactionApplicationService;
  private final TransactionMapper transactionMapper;

  @Autowired
  public TransactionController(TransactionApplicationService transactionApplicationService, TransactionMapper transactionMapper) {
    this.transactionApplicationService = transactionApplicationService;
    this.transactionMapper = transactionMapper;
  }

  @PostMapping(value = "/{cardId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO, @PathVariable("cardId") UUID cardId) {
    log.info("CONTROLLER: Received request to create a new transaction and passing to the application");
    Transaction transaction = transactionApplicationService.createTransaction(transactionCreateDTO, cardId);
    return ResponseEntity.ok(transactionMapper.transactionToTransactionResponseDTO(transaction));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<TransactionResponseDTO>>> getTransaction(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ){
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "timestamp"));
    return ResponseEntity.ok(transactionApplicationService.getTransactions(pageable, username));
  }
}
