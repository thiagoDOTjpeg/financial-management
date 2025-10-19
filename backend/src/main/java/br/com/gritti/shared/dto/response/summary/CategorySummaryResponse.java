package br.com.gritti.shared.dto.response.summary;

import br.com.gritti.domain.enums.TransactionType;

import java.util.UUID;

public class CategorySummaryResponse {
  private UUID id;
  private String name;
  private TransactionType type;
  private String icon;
  private String color;
  private Boolean isActive ;

  public CategorySummaryResponse() {
  }

  public CategorySummaryResponse(UUID id, String name, TransactionType type, String icon, String color, Boolean isActive) {
    this.name = name;
    this.type = type;
    this.icon = icon;
    this.color = color;
    this.isActive = isActive;
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

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
