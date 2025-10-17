package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.model.BankAccount;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "card")
public class CardResponse extends RepresentationModel<CardResponse> {
  private BankAccountResponse bankAccount;
  private String cardBrand;
  private String cardName;
  private BigDecimal creditLimit;
  private Integer closingDay;
  private Integer dueDay;
  private Boolean isActive;

  public CardResponse() {
  }

  public CardResponse(BankAccountResponse bankAccount, String cardBrand, String cardName, BigDecimal creditLimit, Integer closingDay, Integer dueDay, Boolean isActive) {
    this.bankAccount = bankAccount;
    this.cardBrand = cardBrand;
    this.cardName = cardName;
    this.creditLimit = creditLimit;
    this.closingDay = closingDay;
    this.dueDay = dueDay;
    this.isActive = isActive;
  }

  public BankAccountResponse getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(BankAccountResponse bankAccount) {
    this.bankAccount = bankAccount;
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
