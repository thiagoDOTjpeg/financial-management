package br.com.gritti.app.services;

import br.com.gritti.app.models.CreditCard;
import br.com.gritti.app.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

  private CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public List<CreditCard> getAllCards() {
    return cardRepository.findAll();
  }

  public CreditCard createCard(CreditCard card) {
    return cardRepository.save(card);
  }
}
