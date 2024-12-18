package br.com.gritti.app.services;

import br.com.gritti.app.models.CreditCard;
import br.com.gritti.app.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardService {

  private final CreditCardRepository creditCardRepository;

  @Autowired
  public CardService(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  public List<CreditCard> getAllCards() {
    return creditCardRepository.findAll();
  }

  public CreditCard getCardById(UUID id)throws Exception{
    return creditCardRepository.findById(id).orElseThrow(() -> new Exception("Credit Card not Found"));
  }

  public CreditCard createCard(CreditCard card) {
    return creditCardRepository.save(card);
  }

  public CreditCard updateCard(CreditCard card) {
    return creditCardRepository.save(card);
  }
}
