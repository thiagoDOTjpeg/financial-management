package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.application.service.TransactionApplicationService;
import br.com.gritti.app.domain.model.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
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

  @PostMapping(value = "/transactions/{cardId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TransactionResponseDTO>> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO, @PathVariable("cardId") UUID cardId) throws BadRequestException {
    log.info("CONTROLLER: Received request to create a new transaction and passing to the application");
    List<TransactionResponseDTO> transaction = transactionApplicationService.createTransaction(transactionCreateDTO, cardId);
    return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
  }

  @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<TransactionResponseDTO>>> getTransaction(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ){
    log.info("CONTROLLER: Received request to retrieve all categories");
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "timestamp"));
    return ResponseEntity.ok(transactionApplicationService.getTransactions(pageable, username));
  }

  @DeleteMapping(value = "/transactions/{transactionId}")
  public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") UUID transactionId){
    log.info("CONTROLLER: Received request to delete a transaction and passing to the application");
    transactionApplicationService.deleteTransaction(transactionId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/invoices/{invoiceId}/transactions")
  public ResponseEntity<PagedModel<EntityModel<TransactionResponseDTO>>> getInvoiceTransactions(
          @PathVariable("invoiceId") UUID invoiceId,
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction
  ){
    log.info("CONTROLLER: Received request to retrieve all categories and passing to the application");
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "timestamp"));
    return ResponseEntity.ok(transactionApplicationService.getInvoiceTransactions(pageable, invoiceId));
  }
}
