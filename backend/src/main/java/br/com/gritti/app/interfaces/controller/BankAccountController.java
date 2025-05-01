package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.service.BankAccountApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bankaccount")
@Tag(name = "Bank Account", description = "Operações relacionadas a contas de bancos")
public class BankAccountController {
  private final Logger log = org.slf4j.LoggerFactory.getLogger(BankAccountController.class);
  private final BankAccountApplicationService bankAccountApplicationService;

  @Autowired
  public BankAccountController(BankAccountApplicationService bankAccountApplicationService) {
    this.bankAccountApplicationService = bankAccountApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<BankAccountResponseDTO>>> getBankAccounts(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction
  ) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get all bank accounts");
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "bankName"));
    return ResponseEntity.ok(bankAccountApplicationService.getAccounts(pageable));
  }

  public ResponseEntity<BankAccountResponseDTO> getAccountById(UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get bank account with id {}", id);
    return ResponseEntity.ok(bankAccountApplicationService.getAccountById(id));
  }

  public ResponseEntity<BankAccountResponseDTO> createAccount(BankAccountCreateDTO bankAccountCreateDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to create a new bank account");
    return ResponseEntity.ok(bankAccountApplicationService.createAccount(bankAccountCreateDTO));
  }

  public ResponseEntity<BankAccountResponseDTO> updateAccount(UUID id, BankAccountResponseDTO bankAccountDTO) {

  }

  public ResponseEntity<?> deleteAccount(UUID id) {

  }

}
