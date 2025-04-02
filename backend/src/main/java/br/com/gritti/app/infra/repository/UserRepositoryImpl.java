package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.repository.UserRepository;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.infra.persistence.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final JpaUserRepository jpaUserRepository;

  public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Override
  public List<User> findAll() {
    return jpaUserRepository.findAll();
  }

  @Override
  public Optional<User> findById(UUID id) {
    return jpaUserRepository.findById(id);
  }

  @Override
  public void save(User user) {
    jpaUserRepository.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return jpaUserRepository.findByUsername(username);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return jpaUserRepository.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(Email email) {
    return jpaUserRepository.existsByEmail(email);
  }
}
