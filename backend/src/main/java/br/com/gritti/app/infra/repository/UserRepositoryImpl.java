package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.repository.UserRepository;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.infra.persistence.JpaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class.getName());

  @Autowired
  private JpaUserRepository jpaUserRepository;

  @Override
  public List<User> findAll() {
    log.info("Request received from domain and finding all users from the jpa repository");
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
  public void delete(User user) {
    jpaUserRepository.delete(user);
  }

  @Override
  public void inactiveUser(UUID id, String updatedBy) {
    jpaUserRepository.inactivateUser(id, updatedBy);
  }

  public boolean existsById(UUID id) {
    return jpaUserRepository.existsById(id);
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
