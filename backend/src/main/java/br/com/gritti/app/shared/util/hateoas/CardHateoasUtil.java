package br.com.gritti.app.shared.util.hateoas;

import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.application.dto.card.CardUpdateDTO;
import br.com.gritti.app.interfaces.controller.BankAccountController;
import br.com.gritti.app.interfaces.controller.CardController;
import br.com.gritti.app.interfaces.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CardHateoasUtil {
  public static void addLinks(CardResponseDTO cardResponseDTO) {
    cardResponseDTO.add(linkTo(methodOn(CardController.class).getCardById(cardResponseDTO.getId())).withSelfRel().withType("GET"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).deleteCard(cardResponseDTO.getId())).withRel("delete").withType("DELETE"));
    cardResponseDTO.add(linkTo(methodOn(CardController.class).updateCard(cardResponseDTO.getId(), new CardUpdateDTO())).withRel("update").withType("PUT"));
    cardResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(cardResponseDTO.getBankAccount().getUser().getId())).withRel("user").withType("GET"));
    cardResponseDTO.add(linkTo(methodOn(BankAccountController.class).getAccountById(cardResponseDTO.getBankAccount().getId())).withRel("bankAccount").withType("GET"));
  }

  public static void addLinkGet(CardResponseDTO cardResponseDTO) {
    cardResponseDTO.add(linkTo(methodOn(CardController.class).getCardById(cardResponseDTO.getId())).withSelfRel().withType("GET"));
  }
}
