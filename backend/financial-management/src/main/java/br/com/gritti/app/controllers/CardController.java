package br.com.gritti.app.controllers;

import br.com.gritti.app.models.CreditCard;
import br.com.gritti.app.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

  private final CardService cardService;

  @Autowired
  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CreditCard> getAllCards() {
    return cardService.getAllCards();
  }

  @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CreditCard getCardById(@RequestParam UUID id) throws Exception {
    return cardService.getCardById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
    return cardService.createCard(creditCard);
  }

  @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CreditCard updateCreditCard(@RequestBody CreditCard creditCard) {
    return cardService.updateCard(creditCard);
  }
}
