package br.com.gritti.shared.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRoleRequest {
  @NotBlank
  @Size(min = 3, max = 100, message = "O nome da role deve ter entre 3 e 100 caracteres")
  private String name;
  @NotBlank
  @Size(min = 2, max = 100, message = "A descrição da role deve ter entre 2 e 100 caracteres")
  private String description;

  public CreateRoleRequest() {
  }

  public CreateRoleRequest(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
