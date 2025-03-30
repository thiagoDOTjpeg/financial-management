package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {
  @Query("SELECT u from User u WHERE u.username =:username")
  User findByUsername(String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username =:username")
  Boolean existsByUsername(String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email =:email")
  Boolean existsByEmail(Email email);
}
