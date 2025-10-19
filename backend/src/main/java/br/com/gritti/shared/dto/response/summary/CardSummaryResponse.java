package br.com.gritti.shared.dto.response.summary;

import java.math.BigDecimal;
import java.util.UUID;

public class CardSummaryResponse {
  private UUID id;
  private String cardBrand;
  private String cardName;
  private BigDecimal creditLimit;
  private Integer closingDay;
  private Integer dueDay;
  private Boolean isActive;

  public CardSummaryResponse() {
  }

  public CardSummaryResponse(UUID id, String cardBrand, String cardName, BigDecimal creditLimit, Integer closingDay, Integer dueDay, Boolean isActive) {
    this.id = id;
    this.cardBrand = cardBrand;
    this.cardName = cardName;
    this.creditLimit = creditLimit;
    this.closingDay = closingDay;
    this.dueDay = dueDay;
    this.isActive = isActive;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCardBrand() {
    return cardBrand;
  }

  public void setCardBrand(String cardBrand) {
    this.cardBrand = cardBrand;
  }

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }

  public Integer getClosingDay() {
    return closingDay;
  }

  public void setClosingDay(Integer closingDay) {
    this.closingDay = closingDay;
  }

  public Integer getDueDay() {
    return dueDay;
  }

  public void setDueDay(Integer dueDay) {
    this.dueDay = dueDay;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
