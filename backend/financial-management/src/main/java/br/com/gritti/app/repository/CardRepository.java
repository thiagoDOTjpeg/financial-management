package br.com.gritti.app.repository;

import br.com.gritti.app.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CreditCard, UUID> {
}
