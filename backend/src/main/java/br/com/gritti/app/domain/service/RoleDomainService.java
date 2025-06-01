package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.infra.repository.RoleRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleDomainService {
  private final Logger log = LoggerFactory.getLogger(RoleDomainService.class.getName());

  @Autowired
  private RoleRepositoryImpl roleRepositoryImpl;

  public List<Role> getRoles() {
    log.info("DOMAIN: Request received from application and getting all roles from the repository");
    return roleRepositoryImpl.findAll();
  }

  public Role getRoleById(UUID id) {
    log.info("DOMAIN: Request received from application and getting role by id from the repository");
    return roleRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
  }

  public void createRole(Role role) {
    log.info("DOMAIN: Request received from application and saving a new role from the repository");
    roleRepositoryImpl.save(role);
  }
}
