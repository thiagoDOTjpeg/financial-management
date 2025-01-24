package br.com.gritti.app.controller;

import br.com.gritti.app.data.dto.user.UserRequestDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.model.User;
import br.com.gritti.app.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "User Endpoint")
@RestController
@RequestMapping("/v1/user")
public class UserController {

  private final UserServices service;

  @Autowired
  public UserController(UserServices service) {
    this.service = service;
  }

  @Operation(summary = "Creates a user")
  @PostMapping()
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO data) {
    UserResponseDTO response = service.createUser(data);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get a user by his UUID")
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(service.getUserById(id));
  }

  @Operation(summary = "Get all users")
  @GetMapping()
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(service.getAllUsers());
  }


}
