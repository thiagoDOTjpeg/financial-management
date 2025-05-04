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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDomainService implements UserDetailsService {
  private final Logger log = LoggerFactory.getLogger(UserDomainService.class.getName());

  private final UserRepositoryImpl userRepositoryImpl;

  private final RoleRepositoryImpl roleRepositoryImpl;

  private final UserMapper userMapper;

  @Autowired
  public UserDomainService(UserRepositoryImpl userRepositoryImpl, RoleRepositoryImpl roleRepositoryImpl, UserMapper userMapper) {
    this.userRepositoryImpl = userRepositoryImpl;
    this.roleRepositoryImpl = roleRepositoryImpl;
    this.userMapper = userMapper;
  }

  public Page<User> getUsers(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all user from the repository");

    return userRepositoryImpl.findAll(pageable);
  }

  public User getUserById(UUID id) {
    log.info("DOMAIN: Request received from application and getting user { {} } from the repository", id);
    return userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
  }

  public User createUser(User user) {
    log.info("DOMAIN: Request received from application and creating user in the repository: {}", user);
    validateUsernameEmailForCreate(user.getEmail(), user.getUsername());
    Role role = roleRepositoryImpl.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role with not found"));
    user.setRoles(role);
    userRepositoryImpl.save(user);
    return user;
  }

  @Transactional
  public User updateUser(UUID id, User newUser) {
    log.info("DOMAIN: Request received from application and updating user {} and passing to the repository", id);
    User entity = userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    validateUsernameEmailForUpdate(id, newUser.getEmail(), newUser.getUsername());
    userMapper.updateUser(newUser, entity);
    userRepositoryImpl.save(entity);
    return entity;
  }

  public void deleteUser(UUID id) {
    log.info("DOMAIN: Request received from application and removing user {} and passing to the repository", id);
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
    log.info("DOMAIN: Request received from application and adding role {} to user {}", roleName, userId);
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

  public void validateUsernameEmailForCreate(Email email, String username) {
    if(userRepositoryImpl.existsByEmail(email)) {
      throw new EmailAlreadyExistsException("Email " + email + " already exists");
    }
    if(userRepositoryImpl.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException("Username " + username + " already exists");
    }
  }

  public void validateUsernameEmailForUpdate(UUID id, Email email, String username) {
    User existingUser = userRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    if(userRepositoryImpl.existsByEmail(email) && !existingUser.getEmail().equals(email)) {
      throw new EmailAlreadyExistsException("Email " + email + " already exists");
    }
    if(userRepositoryImpl.existsByUsername(username) && !existingUser.getUsername().equals(username)) {
      throw new UsernameAlreadyExistsException("Username " + username + " already exists");
    }
  }
}
