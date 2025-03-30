package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.service.UserApplicationService;
import br.com.gritti.app.domain.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  private final UserApplicationService userApplicationService;

  public UserController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getUsers() {
    return userApplicationService.getUsers();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<User> getUserById(@PathVariable UUID id) {
    return userApplicationService.getUserById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  public User createUser(@RequestBody UserCreateDTO userDTO) {
    return userApplicationService.createUser(userDTO);
  }
}
