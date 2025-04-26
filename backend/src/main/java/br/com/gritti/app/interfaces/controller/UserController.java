package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.UserAssignRoleDTO;
import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.dto.UserUpdateDTO;
import br.com.gritti.app.application.service.UserApplicationService;
import br.com.gritti.app.interfaces.controller.docs.UserControllerDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Operações relacionadas a usuários")
public class UserController implements UserControllerDocs {
  @Autowired
  private UserApplicationService userApplicationService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<CollectionModel<UserResponseDTO>> getUsers() {
    return ResponseEntity.ok(userApplicationService.getUsers());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userApplicationService.getUserById(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userDTO) {
    if(userDTO == null) {
      throw new IllegalArgumentException("The request cannot be empty");
    }
    return ResponseEntity.ok(userApplicationService.createUser(userDTO));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                  produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,@RequestBody UserUpdateDTO userDTO) {
    return ResponseEntity.ok().body(userApplicationService.updateUser(id, userDTO));
  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
    userApplicationService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  @Override
  public ResponseEntity<UserResponseDTO> inactivateUser(@PathVariable UUID id) {
    return ResponseEntity.ok(userApplicationService.inactivateUser(id));
  }

  @PostMapping(value = "/{id}/role", consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> assignRoleToUser(@Valid @RequestBody UserAssignRoleDTO data, @PathVariable(name = "id") UUID id) {
    return ResponseEntity.ok(userApplicationService.assignRoleToUser(id, data.getRoleName()));
  }
}
