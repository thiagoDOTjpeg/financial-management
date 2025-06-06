package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.service.RoleApplicationService;
import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.interfaces.controller.docs.RoleControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "Role", description = "Operações relacionadas a permissões(roles)")
public class RoleController implements RoleControllerDocs {
  private final Logger log = LoggerFactory.getLogger(RoleController.class);
  @Autowired
  private RoleApplicationService roleApplicationService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<Role>> getRoles() {
    log.info("CONTROLLER: Received request to get roles and passing to the application");
    return ResponseEntity.status(200).body(roleApplicationService.getRoles());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Role> getRoleById(@PathVariable("id") UUID id) {
    log.info("CONTROLLER: Received request to get role with id {} and passing to the application", id);
    return ResponseEntity.status(200).body(roleApplicationService.getRoleById(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<?> saveRole(@Valid @RequestBody Role role) {
    if(role == null) throw new IllegalArgumentException("Role cannot be null");
    log.info("CONTROLLER: Received request to save role {} and passing to the application", role);
    roleApplicationService.createRole(role);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
    return ResponseEntity.created(location).body(role);
  }
}
