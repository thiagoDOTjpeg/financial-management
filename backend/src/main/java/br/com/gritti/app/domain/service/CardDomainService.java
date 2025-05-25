package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.infra.repository.BankAccountRepositoryImpl;
import br.com.gritti.app.infra.repository.CardRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardDomainService {
  private final Logger log = LoggerFactory.getLogger(CardDomainService.class);

  private final CardRepositoryImpl cardRepositoryImpl;
  private final BankAccountDomainService bankAccountDomainService;

  @Autowired
  public CardDomainService(CardRepositoryImpl cardRepositoryImpl, BankAccountDomainService bankAccountDomainService) {
    this.cardRepositoryImpl = cardRepositoryImpl;
    this.bankAccountDomainService = bankAccountDomainService;
  }

  public Page<Card> getCards(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all cards from the repository");
    return cardRepositoryImpl.findAll(pageable);
  }

  public Page<Card> getCards(Pageable pageable, String username) {
    log.info("DOMAIN: Request received from application and getting all cards from the repository and filtering by username: {}", username);
    return cardRepositoryImpl.findAllByUsername(pageable, username);
  }

  public Card getCardById(UUID id) {
    log.info("DOMAIN: Request received from application and getting card with id {} from the repository", id);
    return cardRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"));
  }

  public List<Card> getCardsByAccount(UUID bankAccountId) {
    log.info("DOMAIN: Request received from application and getting all cards from the repository and filtering by bank account id: {}", bankAccountId);
    return cardRepositoryImpl.findCardByAccount(bankAccountId);
  }

  public void createCard(Card card, UUID bankAccountId) {
    log.info("DOMAIN: Request received from application and saving a new card from the repository");
    if(card.getCreditLimit() < 0) throw new IllegalArgumentException("Invalid credit limit for card");
    BankAccount bankAccount = bankAccountDomainService.getAccountById(bankAccountId);
    card.setBankAccount(bankAccount);
    cardRepositoryImpl.save(card);
  }

  public void deleteCard(UUID id) {
    log.info("DOMAIN: Request received from application and deleting card from the repository");
    Card card = cardRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"));
    cardRepositoryImpl.delete(card);
  }

  public Card updateCard(UUID id, Card card) {
    log.info("DOMAIN: Request received from application and updating the card with id {}", card.getId());
    if(card.getCreditLimit() < 0) throw new IllegalArgumentException("Invalid credit limit for card");
    Card entity = cardRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"));
    entity.setCreditLimit(card.getCreditLimit());
    entity.setCardBrand(card.getCardBrand());
    cardRepositoryImpl.save(entity);
    return entity;
  }
}
