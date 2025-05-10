package br.com.gritti.app.application.dto.card;

import br.com.gritti.app.application.valueobjects.BankAccountMinimalVO;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;

@Relation(collectionRelation = "card")
public class CardResponseDTO extends RepresentationModel<CardResponseDTO> {
  private UUID id;
  private int creditLimit;
  private String cardBrand;
  private BankAccountMinimalVO bankAccount;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  public CardResponseDTO() {
  }

  public CardResponseDTO(UUID id, int creditLimit, String cardBrand, BankAccountMinimalVO bankAccount,
                         LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
    this.id = id;
    this.creditLimit = creditLimit;
    this.cardBrand = cardBrand;
    this.bankAccount = bankAccount;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
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

  public BankAccountMinimalVO getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(BankAccountMinimalVO bankAccount) {
    this.bankAccount = bankAccount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
