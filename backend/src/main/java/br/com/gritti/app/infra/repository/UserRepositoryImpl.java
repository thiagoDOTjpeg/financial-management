package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.repository.UserRepository;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.infra.persistence.JpaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Page<User> findAll(Pageable pageable) {
    log.info("REPO: Request received from domain and finding all users from the jpa repository");
    return jpaUserRepository.findAll(pageable);
  }

  @Override
  public Optional<User> findById(UUID id) {
    log.info("REPO: Request received from domain and finding user with id {} from the jpa repository", id);
    return jpaUserRepository.findById(id);
  }

  @Override
  public void save(User user) {
    log.info("REPO: Request received from domain and saving user in the jpa repository");
    jpaUserRepository.save(user);
  }

  @Override
  public void delete(User user) {
    log.info("REPO: Request received from domain and deleting user from the jpa repository");
    jpaUserRepository.delete(user);
  }

  @Override
  public void inactiveUser(UUID id, String updatedBy) {
    log.info("REPO: Request received from domain and inactivating user with id {} from the jpa repository", id);
    jpaUserRepository.inactivateUser(id, updatedBy);
  }

  public boolean existsById(UUID id) {
    log.info("REPO: Request received from domain and checking if user with id {} exists in the jpa repository", id);
    return jpaUserRepository.existsById(id);
  }

  @Override
  public User findByUsername(String username) {
    log.info("REPO: Request received from domain and finding user with username {} from the jpa repository", username);
    return jpaUserRepository.findByUsername(username);
  }

  @Override
  public Boolean existsByUsername(String username) {
    log.info("REPO: Request received from domain and checking if user with username {} exists in the jpa repository", username);
    return jpaUserRepository.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(Email email) {
    log.info("REPO: Request received from domain and checking if user with email {} exists in the jpa repository", email);
    return jpaUserRepository.existsByEmail(email);
  }
}
