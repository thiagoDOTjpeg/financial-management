package br.com.gritti.app.repository;

import br.com.gritti.app.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, UUID> {
}
