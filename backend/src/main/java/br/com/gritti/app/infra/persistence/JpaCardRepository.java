package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCardRepository extends JpaRepository<Card, UUID> {
}
