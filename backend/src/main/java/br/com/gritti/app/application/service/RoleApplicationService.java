package br.com.gritti.app.application.service;

import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.service.RoleDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleApplicationService {
  private Logger log = LoggerFactory.getLogger(RoleApplicationService.class);

  @Autowired
  private RoleDomainService roleDomainService;

  public List<Role> getRoles() {
    log.info("APPLICATION: Request received from controller and getting all roles from the repository");
    return roleDomainService.getRoles();
  }

  public Role getRoleById(UUID id) {
    log.info("APPLICATION: Request received from controller and getting role by id from the repository");
    return roleDomainService.getRoleById(id);
  }

  public void save(Role role) {
    log.info("APPLICATION: Request received from controller and saving a new role");
    roleDomainService.save(role);
  }
}
