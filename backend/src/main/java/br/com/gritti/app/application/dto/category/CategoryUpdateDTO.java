package br.com.gritti.app.application.dto.category;

import br.com.gritti.app.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public class CategoryUpdateDTO {
  @NotNull(message = "Name cannot be null")
  private String name;
  @NotNull(message = "Type cannot be null")
  private CategoryType type;

  public CategoryUpdateDTO() {
  }

  public CategoryUpdateDTO(String name, CategoryType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryType getType() {
    return type;
  }

  public void setType(CategoryType type) {
    this.type = type;
  }
}
