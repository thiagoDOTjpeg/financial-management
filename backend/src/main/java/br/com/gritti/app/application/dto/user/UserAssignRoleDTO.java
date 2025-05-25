package br.com.gritti.app.application.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserAssignRoleDTO {
  @NotBlank(message = "Role name cannot be blank")
  private String roleName;

  public UserAssignRoleDTO(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}
