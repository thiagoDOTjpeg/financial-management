package br.com.gritti.app.shared.util;

import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.interfaces.controller.CardController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CardHateoasUtil {
  public static void addLinks(CardResponseDTO cardResponseDTO) {
    cardResponseDTO.add(linkTo(methodOn(CardController.class).getCardById(cardResponseDTO.getId())).withSelfRel().withType("GET"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).getCards(0, 12, "asc", "")).withRel("cards").withType("GET"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).createCard(new CardCreateDTO(), cardResponseDTO.getBankAccount().getId())).withRel("create").withType("POST"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).deleteCard(cardResponseDTO.getId())).withRel("delete").withType("DELETE"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).updateCard(cardResponseDTO.getId(), new CardCreateDTO())).withRel("update").withType("PUT"));
  }

  public static void addLinkGet(CardResponseDTO cardResponseDTO) {
    cardResponseDTO.add(linkTo(methodOn(CardController.class).getCardById(cardResponseDTO.getId())).withSelfRel().withType("GET"));
  }
}
