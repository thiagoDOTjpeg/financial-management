package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.application.dto.card.CardUpdateDTO;
import br.com.gritti.app.application.mapper.CardMapper;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.service.CardDomainService;
import br.com.gritti.app.interfaces.controller.CardController;
import br.com.gritti.app.shared.util.hateoas.CardHateoasUtil;
import br.com.gritti.app.shared.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CardApplicationService {
  private final Logger log = LoggerFactory.getLogger(CardApplicationService.class);

  private final CardMapper cardMapper;
  private final CardDomainService cardDomainService;
  private final PagedResourcesAssembler<CardResponseDTO> assembler;

  @Autowired
  public CardApplicationService(CardDomainService cardDomainService,
                                CardMapper cardMapper, PagedResourcesAssembler<CardResponseDTO> assembler) {
    this.cardMapper = cardMapper;
    this.cardDomainService = cardDomainService;
    this.assembler = assembler;
  }

  public PagedModel<EntityModel<CardResponseDTO>> getCards(Pageable pageable, String username) {
    log.info("APPLICATION: Request received from controller and passing to domain to get all cards");
    String currentUsername = SecurityUtil.getCurrentUsername();
    boolean isAdmin = SecurityUtil.isAdmin();
    String usernameToUse = isAdmin ? username : currentUsername;
    Page<Card> cards;
    if(username != null && !username.isBlank()) {
      if(!isAdmin && !username.equals(currentUsername)){
        throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
      }
      cards = cardDomainService.getCards(pageable, usernameToUse);
    } else {
      cards = cardDomainService.getCards(pageable);
    }

    Page<CardResponseDTO> cardsWithLinks = cards.map(c -> {
      CardResponseDTO cardDto = cardMapper.cardToCardResponseDTO(c);
      CardHateoasUtil.addLinks(cardDto);
      return cardDto;
    });


    Link findAllLinks = linkTo(methodOn(CardController.class).getCards(pageable.getPageNumber(), pageable.getPageSize(), "asc", "")).withSelfRel();
    Link createLinks = linkTo(methodOn(CardController.class).createCard(new CardCreateDTO(), UUID.fromString("uuid-example"))).withRel("create-card");
    PagedModel<EntityModel<CardResponseDTO>> pagedModel = assembler.toModel(cardsWithLinks, findAllLinks);
    pagedModel.add(createLinks);
    return pagedModel;

  }

  public CardResponseDTO getCardById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to get a card by id: {}", id);
    Card card = cardDomainService.getCardById(id);
    return cardMapper.cardToCardResponseDTO(card);
  }

  public CardResponseDTO createCard(CardCreateDTO cardCreateDTO, UUID bankAccountId) {
    log.info("APPLICATION: Request received from controller and passing to the domain to create a new card");
    Card card = cardMapper.cardCreateDTOtoCard(cardCreateDTO);
    cardDomainService.createCard(card, bankAccountId);
    return cardMapper.cardToCardResponseDTO(card);
  }

  public void deleteCard(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to delete a card by id: {}", id);
    cardDomainService.deleteCard(id);
  }

  public CardResponseDTO updateCard(UUID id, CardUpdateDTO cardUpdateDTO) {
    log.info("APPLICATION: Request received from controller and passing to the domain to update a card by id: {}", id);
    Card card = cardMapper.cardUpdateDTOtoCard(cardUpdateDTO);
    Card updatedCard = cardDomainService.updateCard(id, card);
    return cardMapper.cardToCardResponseDTO(updatedCard);
  }

}
