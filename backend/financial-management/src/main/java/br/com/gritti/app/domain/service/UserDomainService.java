package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDomainService implements UserDetailsService {
  private Logger logger = LoggerFactory.getLogger(UserDomainService.class.getName());

  private final UserRepositoryImpl userRepositoryImpl;

  public UserDomainService(UserRepositoryImpl userRepositoryImpl) {
    this.userRepositoryImpl = userRepositoryImpl;
  }

  public List<User> getUsers() {
    return userRepositoryImpl.findAll();
  }

  public Optional<User> getUserById(UUID id) {
    return userRepositoryImpl.findById(id);
  }

  public User createUser(User user) {
    logger.info("User created: " + user);
    userRepositoryImpl.save(user);
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepositoryImpl.findByUsername(username);
    if(user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found");
    }
  }
}
