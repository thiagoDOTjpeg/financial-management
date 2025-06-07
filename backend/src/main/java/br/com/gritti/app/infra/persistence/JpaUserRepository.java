package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {
  @Query("SELECT u from User u WHERE u.username = :username")
  User findByUsername(@Param("username")String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
  boolean existsByUsername(@Param("username") String username);


  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
  boolean existsByEmail(@Param("email") Email email);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE User p SET p.accountStatus = 'INACTIVE', p.updatedAt = CURRENT_TIMESTAMP, p.updatedBy = :updatedBy WHERE p.id = :id")
  void inactivateUser(@Param("id") UUID id, @Param("updatedBy") String updatedBy);
}
