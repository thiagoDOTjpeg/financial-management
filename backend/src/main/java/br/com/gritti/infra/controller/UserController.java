package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.UserServiceImpl;
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
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserServiceImpl userServiceImpl;

  @Autowired
  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    UserResponse response = userServiceImpl.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
    UserResponse response = userServiceImpl.getUserById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
    UserResponse response = userServiceImpl.getUserByUsername(username);
    return ResponseEntity.ok(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<UserResponse>>> getAllUsers(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          PagedResourcesAssembler<UserResponse> pagedResourcesAssembler
  ) {
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "username"));
    PagedModel<EntityModel<UserResponse>> users = pagedResourcesAssembler.toModel(userServiceImpl.getAllUsers(pageable));
    return ResponseEntity.ok(users);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(
          @PathVariable UUID id,
          @Valid @RequestBody UpdateUserRequest request) {
    UserResponse response = userServiceImpl.updateUser(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    userServiceImpl.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/exists/username/{username}")
  public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
    boolean exists = userServiceImpl.existsByUsername(username);
    return ResponseEntity.ok(exists);
  }

  @GetMapping("/exists/email/{email}")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    boolean exists = userServiceImpl.existsByEmail(email);
    return ResponseEntity.ok(exists);
  }

}
