package br.com.gritti.app.controllers;

import br.com.gritti.app.dto.PaginationDTO;
import br.com.gritti.app.models.Transaction;
import br.com.gritti.app.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {


  @Autowired
  private TransactionService transactionService;

  @GetMapping
  public ResponseEntity<PaginationDTO<Transaction>> listAllTransaction(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Page<Transaction> allTransaction = transactionService.listAllTransaction(page, size);
    return ResponseEntity.ok(new PaginationDTO<>(allTransaction));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Transaction createTransaction(@RequestBody Transaction transaction) {
    return transactionService.createTransaction(transaction);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteTransaction(@PathVariable String id) throws Exception {
    transactionService.deleteTransaction(id);
    return ResponseEntity.ok().build();
  }
}
