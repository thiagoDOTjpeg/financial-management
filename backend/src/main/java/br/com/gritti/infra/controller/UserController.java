package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.UserServiceImpl;
import br.com.gritti.infra.controller.contract.UserApi;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.user.CreateUserRequest;
import br.com.gritti.shared.dto.request.user.UpdateUserRequest;
import br.com.gritti.shared.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController implements UserApi {
  private final UserServiceImpl userServiceImpl;

  @Autowired
  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  @Override
  public ResponseEntity<UserResponse> createUser(CreateUserRequest request) {
    UserResponse response = userServiceImpl.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  public ResponseEntity<UserResponse> getUserById(UUID id) {
    UserResponse response = userServiceImpl.getUserById(id);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<UserResponse> getUserByUsername(String username) {
    UserResponse response = userServiceImpl.getUserByUsername(username);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<PagedModel<EntityModel<UserResponse>>> getAllUsers(
          @AuthenticatedUserId UUID userId, Integer page, Integer size, String direction,
          PagedResourcesAssembler<UserResponse> pagedResourcesAssembler
  ) {
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "username"));
    PagedModel<EntityModel<UserResponse>> users = pagedResourcesAssembler.toModel(userServiceImpl.getAllUsers(pageable));
    return ResponseEntity.ok(users);
  }

  @Override
  public ResponseEntity<UserResponse> updateUser(UUID id, UpdateUserRequest request) {
    UserResponse response = userServiceImpl.updateUser(id, request);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteUser(UUID id) {
    userServiceImpl.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Boolean> existsByUsername(String username) {
    boolean exists = userServiceImpl.existsByUsername(username);
    return ResponseEntity.ok(exists);
  }

  @Override
  public ResponseEntity<Boolean> existsByEmail(String email) {
    boolean exists = userServiceImpl.existsByEmail(email);
    return ResponseEntity.ok(exists);
  }

}
