package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.application.dto.card.CardUpdateDTO;
import br.com.gritti.app.application.service.CardApplicationService;
import br.com.gritti.app.interfaces.controller.docs.CardControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1/card")
@Tag(name = "Card", description = "Operações relacionadas a cartões")
public class CardController implements CardControllerDocs {
  private final Logger log = LoggerFactory.getLogger(CardController.class);
  private final CardApplicationService cardApplicationService;

  @Autowired
  public CardController(CardApplicationService cardApplicationService) {
    this.cardApplicationService = cardApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<PagedModel<EntityModel<CardResponseDTO>>> getCards(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get all cards");
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "cardBrand"));
    return ResponseEntity.ok(cardApplicationService.getCards(pageable, username));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override

  public ResponseEntity<CardResponseDTO> getCardById(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get card with id {}", id);
    return ResponseEntity.ok(cardApplicationService.getCardById(id));
  }

  @DeleteMapping(value = "/{id}")
  @Override
  public ResponseEntity<?> deleteCard(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to delete card with id {}", id);
    cardApplicationService.deleteCard(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<CardResponseDTO> createCard(
          @Valid @RequestBody CardCreateDTO cardCreateDTO,
          @RequestParam(value = "bankAccountId", required = true) UUID bankAccountId) {
    log.info("CONTROLLER: Request received from the client and passing to the application to save a new card");
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
    return ResponseEntity.created(location).body(cardApplicationService.createCard(cardCreateDTO, bankAccountId));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public CardResponseDTO updateCard(@PathVariable UUID id, @Valid @RequestBody CardUpdateDTO cardUpdateDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to update a card");
    return cardApplicationService.updateCard(id, cardUpdateDTO);
  }
}
