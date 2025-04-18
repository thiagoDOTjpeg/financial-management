package br.com.gritti.app.infra.repository;


import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.repository.RoleRepository;
import br.com.gritti.app.infra.persistence.JpaRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
  private Logger log = LoggerFactory.getLogger(RoleRepositoryImpl.class);

  @Autowired
  private JpaRoleRepository jpaRoleRepository;


  @Override
  public List<Role> findAll() {
    log.info("REPOSITORY: Request received from domain and getting all roles from the database");
    return jpaRoleRepository.findAll();
  }

  @Override
  public Optional<Role> findById(UUID id) {
    log.info("REPOSITORY: Request received from domain and getting role with id {} from the database", id);
    return jpaRoleRepository.findById(id);
  }

  @Override
  public void save(Role role) {
    log.info("REPOSITORY: Request received from domain and saving role {} in the database", role);
    jpaRoleRepository.save(role);
  }
}
