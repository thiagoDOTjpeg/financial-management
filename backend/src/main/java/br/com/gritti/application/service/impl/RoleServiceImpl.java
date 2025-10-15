package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.RoleService;
import br.com.gritti.domain.model.Role;
import br.com.gritti.domain.repository.RoleRepository;
import br.com.gritti.shared.dto.request.role.CreateRoleRequest;
import br.com.gritti.shared.dto.request.role.UpdateRoleRequest;
import br.com.gritti.shared.dto.response.RoleResponse;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public RoleResponse getById(UUID id) {
    Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
    return RoleMapper.toResponse(role);
  }

  @Override
  public List<RoleResponse> getAllRoles() {
    List<Role> roles = roleRepository.findAll();
    return roles.stream().map(RoleMapper::toResponse).toList();
  }

  @Override
  public RoleResponse updateRole(UUID id, UpdateRoleRequest request) {
    Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
    role.setName(request.name());
    role.setDescription(request.description());
    Role updatedRole = roleRepository.save(role);
    return RoleMapper.toResponse(updatedRole);
  }

  @Override
  public void deleteRole(UUID id) {
    Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
    roleRepository.delete(role);
  }

  @Override
  public RoleResponse createRole(CreateRoleRequest request) {
    Role role = RoleMapper.toEntity(request);
    Role savedRole = roleRepository.save(role);
    return RoleMapper.toResponse(savedRole);
  }
}
