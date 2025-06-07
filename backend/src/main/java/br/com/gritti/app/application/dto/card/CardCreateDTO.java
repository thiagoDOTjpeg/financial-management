package br.com.gritti.app.application.dto.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CardCreateDTO {
  @NotNull(message = "Credit limit cannot be null")
  private int creditLimit;
  @NotBlank(message = "Card brand cannot be blank")
  private String cardBrand;
  @NotNull(message = "Due day cannot be blank")
  private int closingDay;
  @NotNull(message = "Due day cannot be blank")
  private int dueDay;

  public CardCreateDTO() {
  }

  public CardCreateDTO(String cardBrand, int creditLimit, int closingDay, int dueDay) {
    this.cardBrand = cardBrand;
    this.creditLimit = creditLimit;
    this.closingDay = closingDay;
    this.dueDay = dueDay;
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

  public int getClosingDay() {
    return closingDay;
  }

  public void setClosingDay(int closingDay) {
    this.closingDay = closingDay;
  }

  public int getDueDay() {
    return dueDay;
  }

  public void setDueDay(int dueDay) {
    this.dueDay = dueDay;
  }
}
