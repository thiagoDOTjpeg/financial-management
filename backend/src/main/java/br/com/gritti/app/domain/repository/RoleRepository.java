package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
  List<Role> findAll();
  Optional<Role> findById(UUID id);
  void save(Role role);
}
