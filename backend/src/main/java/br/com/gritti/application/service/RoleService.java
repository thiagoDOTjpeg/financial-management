package br.com.gritti.application.service;

import br.com.gritti.domain.model.Role;
import br.com.gritti.shared.dto.request.role.CreateRoleRequest;
import br.com.gritti.shared.dto.request.role.UpdateRoleRequest;
import br.com.gritti.shared.dto.response.RoleResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
  RoleResponse getById(UUID id);
  List<RoleResponse> getAllRoles();
  RoleResponse updateRole(UUID id, UpdateRoleRequest request);
  void deleteRole(UUID id);
  RoleResponse createRole(CreateRoleRequest request);
}
