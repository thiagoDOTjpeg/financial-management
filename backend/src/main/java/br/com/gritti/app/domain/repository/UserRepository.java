package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository  {
  Page<User> findAll(Pageable pageable);
  Optional<User> findById(UUID id);
  void save(User user);
  void delete(User user);
  void inactiveUser(UUID id, String updatedBy);
  User findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(Email email);
}
