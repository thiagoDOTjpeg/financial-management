package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.repository.CardRepository;
import br.com.gritti.app.infra.persistence.JpaCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CardRepositoryImpl implements CardRepository {
  private final Logger log = LoggerFactory.getLogger(CardRepositoryImpl.class);
  private final JpaCardRepository jpaCardRepository;

  @Autowired
  public CardRepositoryImpl(JpaCardRepository jpaCardRepository) {
    this.jpaCardRepository = jpaCardRepository;
  }

  @Override
  public Page<Card> findAll(Pageable pageable ) {
    log.info("REPO: Request received from domain and finding all cards from the database");
    return jpaCardRepository.findAll(pageable);
  }

  @Override
  public Page<Card> findAllByUsername(Pageable pageable, String username) {
    log.info("REPO: Request received from domain and finding all cards from the database and filtering by username: {}", username);
    return jpaCardRepository.findAllByUsername(username, pageable);
  }

  @Override
  public List<Card> findCardByAccount(UUID bankAccountId) {
    log.info("REPO: Request received from domain and finding all cards from the database and filtering by bank account id: {}", bankAccountId);
    return jpaCardRepository.findCardByAccount(bankAccountId);
  }

  @Override
  public Optional<Card> findById(UUID id) {
    log.info("REPO: Request received from domain and finding card with id {} from the database", id);
    return jpaCardRepository.findById(id);
  }

  @Override
  public void save(Card card) {
    log.info("REPO: Request received from domain and saving card in the database");
    jpaCardRepository.save(card);
  }

  @Override
  public void delete(Card card) {
    log.info("REPO: Request received from domain and deleting card from the database");
    jpaCardRepository.delete(card);
  }
}
