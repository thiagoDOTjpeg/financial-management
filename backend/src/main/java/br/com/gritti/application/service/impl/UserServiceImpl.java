package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.UserService;
import br.com.gritti.domain.model.User;
import br.com.gritti.domain.repository.UserRepository;
import br.com.gritti.shared.dto.request.user.CreateUserRequest;
import br.com.gritti.shared.dto.request.user.UpdateUserRequest;
import br.com.gritti.shared.dto.response.UserResponse;
import br.com.gritti.shared.exception.BusinessException;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserResponse createUser(CreateUserRequest request) {
    if (userRepository.existsByUsername(request.username())) {
      throw new BusinessException("Username já está em uso", "USERNAME_ALREADY_EXISTS");
    }

    if (userRepository.existsByEmail(request.email())) {
      throw new BusinessException("Email já está em uso", "EMAIL_ALREADY_EXISTS");
    }

    String encodedPassword = passwordEncoder.encode(request.password());

    User user = UserMapper.toEntity(request, encodedPassword);
    User savedUser = userRepository.save(user);

    return UserMapper.toResponse(savedUser);
  }

  @Override
  @Transactional()
  public UserResponse getUserById(UUID id) {
    User user = userRepository.findByIdAndNotDeleted(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

    return UserMapper.toResponse(user);
  }

  @Override
  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsernameAndDeletedAtIsNull(username)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + username));

    return UserMapper.toResponse(user);
  }

  @Override
  public Page<UserResponse> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(UserMapper::toResponse);
  }

  @Override
  public UserResponse updateUser(UUID id, UpdateUserRequest request) {
    User user = userRepository.findByIdAndNotDeleted(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

    if (request.username() != null && !request.username().equals(user.getUsername())) {
      if (userRepository.existsByUsername(request.username())) {
        throw new BusinessException("Username já está em uso", "USERNAME_ALREADY_EXISTS");
      }
      user.setUsername(request.username());
    }

    if (request.email() != null && !request.email().equals(user.getEmail())) {
      if (userRepository.existsByEmail(request.email())) {
        throw new BusinessException("Email já está em uso", "EMAIL_ALREADY_EXISTS");
      }
      user.setEmail(request.email());
    }

    if (request.password() != null) {
      String encodedPassword = passwordEncoder.encode(request.password());
      user.setPassword(encodedPassword);
    }

    if (request.fullName() != null) {
      user.setFullName(request.fullName());
    }

    User updatedUser = userRepository.save(user);
    return UserMapper.toResponse(updatedUser);
  }

  @Override
  public void deleteUser(UUID id) {
    User user = userRepository.findByIdAndNotDeleted(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

    user.softDelete();
    userRepository.save(user);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
  }
}
