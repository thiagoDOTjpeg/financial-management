package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "Operações relacionadas a usuários")
public class UserController {
  private final UserApplicationService userApplicationService;

  public UserController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Obter todos os usuários")
  public ResponseEntity<List<UserResponseDTO>> getUsers() {
    return ResponseEntity.ok(userApplicationService.getUsers());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Criar um novo usuário")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userApplicationService.getUserById(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Obter um usuário por ID")
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userDTO) {
    return ResponseEntity.ok(userApplicationService.createUser(userDTO));
  }
}
