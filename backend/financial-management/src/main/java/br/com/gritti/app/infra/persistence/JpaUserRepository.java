package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {
  @Query("SELECT u from User u WHERE u.username =:username")
  User findByUsername(String username);
}
