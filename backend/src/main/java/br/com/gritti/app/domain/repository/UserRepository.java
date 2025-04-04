package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository  {
  List<User> findAll();
  Optional<User> findById(UUID id);
  void save(User user);
  User findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(Email email);
}
