package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaCardRepository extends JpaRepository<Card, UUID> {
  @Query("SELECT c from Card c WHERE c.bankAccount.user.username LIKE LOWER(CONCAT('%',:username,'%'))")
  Page<Card> findAllByUsername(@Param("username") String username, Pageable pageable);

  @Query("SELECT c FROM Card c WHERE c.bankAccount.id = :bankAccountId")
  List<Card> findCardByAccount(@Param("bankAccountId") UUID bankAccountId);
}
