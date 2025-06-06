package br.com.gritti.app.application.dto.minimal;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class CardMinimalDTO extends RepresentationModel<CardMinimalDTO> {
  private UUID id;
  private int creditLimit;
  private String cardBrand;

  public CardMinimalDTO() {
  }

  public CardMinimalDTO(UUID id, int creditLimit, String cardBrand) {
    this.id = id;
    this.creditLimit = creditLimit;
    this.cardBrand = cardBrand;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(int creditLimit) {
    this.creditLimit = creditLimit;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }
}
