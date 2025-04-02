package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.shared.exceptions.EmailAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDomainService implements UserDetailsService {
  private final Logger logger = LoggerFactory.getLogger(UserDomainService.class.getName());

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

  public void validateUsernameEmail(String email, String username) {
    Email emailObj = new Email(email);
    if(userRepositoryImpl.existsByEmail(emailObj)) {
      throw new EmailAlreadyExistsException("Email " + email + " already exists");
    }
    if(userRepositoryImpl.existsByUsername(username)) {
      throw new UsernameNotFoundException("Username " + username + " already exists");
    }
  }

}
