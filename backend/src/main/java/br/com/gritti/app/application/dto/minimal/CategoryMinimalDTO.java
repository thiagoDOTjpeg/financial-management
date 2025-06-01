package br.com.gritti.app.application.dto.minimal;

import br.com.gritti.app.domain.enums.CategoryType;

public class CategoryMinimalDTO {
  private String name;
  private CategoryType type;

  public CategoryMinimalDTO() {
  }

  public CategoryMinimalDTO(String name, CategoryType type) {
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
