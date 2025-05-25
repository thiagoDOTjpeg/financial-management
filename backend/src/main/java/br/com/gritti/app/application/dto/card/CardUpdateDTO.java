package br.com.gritti.app.application.dto.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CardUpdateDTO {
  @NotNull(message = "Credit limit cannot be null")
  private int creditLimit;
  @NotBlank(message = "Card brand cannot be blank")
  private String cardBrand;

  public CardUpdateDTO() {
  }

  public CardUpdateDTO(int creditLimit, String cardBrand) {
    this.creditLimit = creditLimit;
    this.cardBrand = cardBrand;
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
