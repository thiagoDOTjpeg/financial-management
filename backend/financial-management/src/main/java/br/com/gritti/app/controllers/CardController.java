package br.com.gritti.app.controllers;

import br.com.gritti.app.models.CreditCard;
import br.com.gritti.app.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.Card;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

  @Autowired
  private CardService cardService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CreditCard> getAllCards() {
    return cardService.getAllCards();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
    return cardService.createCard(creditCard);
  }
}
