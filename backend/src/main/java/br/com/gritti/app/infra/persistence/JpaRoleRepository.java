package br.com.gritti.app.infra.persistence;

import br.com.gritti.app.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, UUID> {
  @Query("SELECT r from Role r WHERE r.description = :name")
  public Optional<Role> findByName(String name);
}
