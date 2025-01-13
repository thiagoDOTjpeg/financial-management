package br.com.gritti.app.controller;

import br.com.gritti.app.data.dto.user.UserCreateDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO data) {
    UserResponseDTO response = service.createUser(data);
    return ResponseEntity.ok(response);
  }
}
