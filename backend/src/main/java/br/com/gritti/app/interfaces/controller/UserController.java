package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.user.*;
import br.com.gritti.app.application.service.UserApplicationService;
import br.com.gritti.app.interfaces.controller.docs.UserControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Operações relacionadas a usuários")
public class UserController implements UserControllerDocs {
  private final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserApplicationService userApplicationService;

  @Autowired
  public UserController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<PagedModel<EntityModel<UserResponseDTO>>> getUsers(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction
  ) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get all users");
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "fullName"));
    return ResponseEntity.ok(userApplicationService.getUsers(pageable));
  }

  @GetMapping(value = "/{id}/bankaccounts", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserBankAccountsResponseDTO> getUserBankAccounts(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get bank accounts from the id {}", id);
    return ResponseEntity.ok(userApplicationService.getUserBankAccounts(id));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get user with id {}", id);
    return ResponseEntity.ok(userApplicationService.getUserById(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to create a new user");
    if(userDTO == null) {
      throw new IllegalArgumentException("The request cannot be empty");
    }
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
    return ResponseEntity.created(location).body(userApplicationService.createUser(userDTO));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                  produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,@RequestBody UserUpdateDTO userDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to update a user");
    return ResponseEntity.ok().body(userApplicationService.updateUser(id, userDTO));
  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to delete a user");
    userApplicationService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  @Override
  public ResponseEntity<UserResponseDTO> inactivateUser(@PathVariable UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to inactivate a user");
    return ResponseEntity.ok(userApplicationService.inactivateUser(id));
  }

  @PostMapping(value = "/{id}/role", consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponseDTO> assignRoleToUser(@Valid @RequestBody UserAssignRoleDTO data, @PathVariable(name = "id") UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to assign a role to a user");
    return ResponseEntity.ok(userApplicationService.assignRoleToUser(id, data.getRoleName()));
  }
}
