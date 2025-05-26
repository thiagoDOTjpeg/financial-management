package br.com.gritti.app.application.valueobjects;

import br.com.gritti.app.domain.enums.CategoryType;

public class CategoryMinimalVO {
  private String name;
  private CategoryType type;

  public CategoryMinimalVO() {
  }

  public CategoryMinimalVO(String name, CategoryType type) {
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
