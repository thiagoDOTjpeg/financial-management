package br.com.gritti.app.application.dto.card;

public class CardCreateDTO {
  private int creditLimit;
  private String cardBrand;

  public CardCreateDTO() {
  }

  public CardCreateDTO(String cardBrand, int creditLimit) {
    this.cardBrand = cardBrand;
    this.creditLimit = creditLimit;
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
