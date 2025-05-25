package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCategoryRepository extends JpaRepository<Category, UUID> {
  @Query("SELECT c from Category c WHERE c.user.username LIKE LOWER(CONCAT('%',:username,'%'))")
  Page<Category> findAllByUsername(@Param("username") String username, Pageable pageable);

}
