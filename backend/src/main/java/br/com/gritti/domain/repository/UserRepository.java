package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  @Query("SELECT u FROM User u WHERE u.username = :username")
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsernameAndDeletedAtIsNull(String username);

  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  List<User> findAllByDeletedAtIsNull();

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  @Modifying
  @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
  void softDelete(@Param("id") UUID id);

  @Query("SELECT u FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
  Optional<User> findByIdAndNotDeleted(@Param("id") UUID id);

}
