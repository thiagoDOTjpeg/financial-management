package br.com.gritti.application.service;

import br.com.gritti.shared.dto.request.user.CreateUserRequest;
import br.com.gritti.shared.dto.request.user.UpdateUserRequest;
import br.com.gritti.shared.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService {
  UserResponse createUser(CreateUserRequest  request);

  UserResponse getUserById(UUID id);

  UserResponse getUserByUsername(String username);

  PagedModel<EntityModel<UserResponse>> getAllUsers(Pageable pageable);

  UserResponse updateUser(UUID id, UpdateUserRequest request);

  void deleteUser(UUID id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

}
