package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository {
  Page<Card> findAll(Pageable pageable);
  Optional<Card> findById(UUID id);
  void save(Card card);
  void delete(Card card);
}
