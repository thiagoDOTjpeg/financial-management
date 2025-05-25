package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCardsResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.application.service.BankAccountApplicationService;
import br.com.gritti.app.interfaces.controller.docs.BankAccountControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bankaccount")
@Tag(name = "Bank Account", description = "Operações relacionadas a contas de bancos")
public class BankAccountController implements BankAccountControllerDocs {
  private final Logger log = org.slf4j.LoggerFactory.getLogger(BankAccountController.class);
  private final BankAccountApplicationService bankAccountApplicationService;

  @Autowired
  public BankAccountController(BankAccountApplicationService bankAccountApplicationService) {
    this.bankAccountApplicationService = bankAccountApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<PagedModel<EntityModel<BankAccountResponseDTO>>> getBankAccounts(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get all bank accounts");
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "bankName"));
    return ResponseEntity.ok(bankAccountApplicationService.getAccounts(pageable, username));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<BankAccountResponseDTO> getAccountById(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get bank account with id {}", id);
    return ResponseEntity.ok(bankAccountApplicationService.getAccountById(id));
  }

  @GetMapping(value = "/{id}/cards", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<BankAccountCardsResponseDTO> getAccountCards(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get bank account cards with the id {}", id);
    return ResponseEntity.ok(bankAccountApplicationService.getAccountCards(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<BankAccountResponseDTO> createAccount(@Valid @RequestBody BankAccountCreateDTO bankAccountCreateDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to create a new bank account");
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
    return ResponseEntity.created(location).body(bankAccountApplicationService.createAccount(bankAccountCreateDTO));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<BankAccountResponseDTO> updateAccount(@PathVariable UUID id,  @Valid @RequestBody BankAccountUpdateDTO bankAccountDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to update a bank account");
    return ResponseEntity.ok(bankAccountApplicationService.updateAccount(id, bankAccountDTO));
  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<?> deleteAccount(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to delete a bank account");
    bankAccountApplicationService.deleteAccount(id);
    return ResponseEntity.noContent().build();
  }
}
