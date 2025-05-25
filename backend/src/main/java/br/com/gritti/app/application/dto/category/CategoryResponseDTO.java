package br.com.gritti.app.application.dto.category;

import br.com.gritti.app.application.valueobjects.UserMinimalVO;
import br.com.gritti.app.domain.enums.CategoryType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(collectionRelation = "categories")
public class CategoryResponseDTO extends RepresentationModel<CategoryResponseDTO> {
  private UUID id;
  private String name;
  private CategoryType type;
  private UserMinimalVO user;

  public CategoryResponseDTO() {
  }

  public CategoryResponseDTO(UUID id, String name, CategoryType type, UserMinimalVO user) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.user = user;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public UserMinimalVO getUser() {
    return user;
  }

  public void setUser(UserMinimalVO user) {
    this.user = user;
  }
}
