package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Role;
import br.com.gritti.shared.dto.request.role.CreateRoleRequest;
import br.com.gritti.shared.dto.response.RoleResponse;

public class RoleMapper {
  public RoleMapper() {
  }

  public static Role toEntity(CreateRoleRequest request) {
    return new Role.Builder()
            .name(request.name())
            .description(request.description())
            .build();
  }

  public static RoleResponse toResponse(Role role) {
    RoleResponse roleResponse = new RoleResponse();
    roleResponse.setId(role.getId());
    roleResponse.setName(role.getName());
    roleResponse.setDescription(role.getDescription());
    roleResponse.setCreatedAt(role.getCreatedAt());
    return roleResponse;
  }
}
