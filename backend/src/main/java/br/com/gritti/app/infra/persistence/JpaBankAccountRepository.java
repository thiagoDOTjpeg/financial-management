package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaBankAccountRepository extends JpaRepository<BankAccount, UUID> {
  @Query("SELECT b from BankAccount b WHERE b.user.username LIKE LOWER(CONCAT('%',:username,'%'))")
  Page<BankAccount> findAllByUsername(@Param("username") String username, Pageable pageable);
}
