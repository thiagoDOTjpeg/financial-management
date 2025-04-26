package br.com.gritti.app.domain.service;

import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.infra.repository.RoleRepositoryImpl;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.shared.exceptions.EmailAlreadyExistsException;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import br.com.gritti.app.shared.exceptions.UsernameAlreadyExistsException;
import br.com.gritti.app.shared.exceptions.UsernameNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
public class UserDomainService implements UserDetailsService {
  private final Logger log = LoggerFactory.getLogger(UserDomainService.class.getName());

  @Autowired
  private UserRepositoryImpl userRepositoryImpl;

  @Autowired
  private RoleRepositoryImpl roleRepositoryImpl;

  @Autowired
  private UserMapper userMapper;


  public List<User> getUsers() {
    log.info("DOMAIN: Request received from application and getting all user from the repository");

    return userRepositoryImpl.findAll();
  }

  public User getUserById(UUID id) {
    log.info("DOMAIN: Request received from application and getting user { " + id + " } from the repository");
    return userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
  }

  public User createUser(User user) {
    log.info("DOMAIN: Request received from application and creating user in the repository: " + user);
    Role role = roleRepositoryImpl.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role with not found"));
    user.setRoles(role);
    userRepositoryImpl.save(user);
    return user;
  }

  @Transactional
  public User updateUser(UUID id, User newUser) {
    User entity = userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    userMapper.updateUser(newUser, entity);
    userRepositoryImpl.save(entity);
    return entity;
  }

  public void deleteUser(UUID id) {
    log.info("DOMAIN: Request received from application and removing user " + id + " and passing to the repository");
    User user = userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    userRepositoryImpl.delete(user);
  }

  @Transactional
  public User inactivateUser(UUID id) {
    log.info("DOMAIN: Request received from application and inactivating user {}", id);
    if(!userRepositoryImpl.existsById(id)) throw new ResourceNotFoundException("User with id " + id + " not found");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userRepositoryImpl.inactiveUser(id, authentication.getName());
    return userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
  }

  public User assignRoleToUser(UUID userId, String roleName) {
    log.info("DOMAIN: Request received from application and adding role " + roleName + " to user " + userId);
    User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    Role role = roleRepositoryImpl.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role with name " + roleName + " not found"));
    if(!user.getPermissions().toString().contains(roleName)) {
      user.setRoles(role);
      userRepositoryImpl.save(user);
    } else {
      throw new IllegalArgumentException("Role with name " + roleName + " already exists in the user " + userId);
    }
    return user;
  }

  @Override
  public User loadUserByUsername(String username) {
    User user = userRepositoryImpl.findByUsername(username);
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
      throw new UsernameAlreadyExistsException("Username " + username + " already exists");
    }
  }
}
