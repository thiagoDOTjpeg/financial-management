package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaTransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query("SELECT t from Transaction t WHERE t.category.user.username LIKE LOWER(CONCAT('%', :username, '%'))")
  public Page<Transaction> findAllByUsername(Pageable pageable, @Param("username") String username);
}
