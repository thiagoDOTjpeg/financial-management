package br.com.gritti.app.repository;

import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
}
