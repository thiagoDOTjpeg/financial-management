package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.RoleServiceImpl;
import br.com.gritti.application.service.impl.UserServiceImpl;
import br.com.gritti.domain.model.Role;
import br.com.gritti.shared.dto.request.role.CreateRoleRequest;
import br.com.gritti.shared.dto.request.role.UpdateRoleRequest;
import br.com.gritti.shared.dto.response.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
  private final RoleServiceImpl roleServiceImpl;

  @Autowired
  public RoleController(RoleServiceImpl roleServiceImpl) {
    this.roleServiceImpl = roleServiceImpl;
  }

  @PostMapping
  public ResponseEntity<RoleResponse> createRole(@RequestBody CreateRoleRequest request) {
    RoleResponse response = roleServiceImpl.createRole(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleResponse> updateRole(@PathVariable UUID id, UpdateRoleRequest request) {
    RoleResponse response = roleServiceImpl.updateRole(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleResponse> getById(@PathVariable UUID id) {
    RoleResponse response = roleServiceImpl.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<List<RoleResponse>> getAllRoles() {
    List<RoleResponse> roles = roleServiceImpl.getAllRoles();
    return ResponseEntity.status(HttpStatus.OK).body(roles);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
    roleServiceImpl.deleteRole(id);
    return ResponseEntity.noContent().build();
  }

}
