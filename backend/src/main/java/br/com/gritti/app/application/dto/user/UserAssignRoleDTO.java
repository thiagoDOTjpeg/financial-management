package br.com.gritti.app.application.dto.user;

public class UserAssignRoleDTO {
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
