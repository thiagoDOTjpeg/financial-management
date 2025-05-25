package br.com.gritti.app.application.dto.bankaccount;

import br.com.gritti.app.application.valueobjects.CardMinimalVO;
import br.com.gritti.app.application.valueobjects.UserMinimalVO;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BankAccountCardsResponseDTO extends RepresentationModel<BankAccountCardsResponseDTO> {
  private UUID id;
  private String bankName;
  private Double balance;
  private UserMinimalVO user;
  private List<CardMinimalVO> cards;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  public BankAccountCardsResponseDTO() {
  }

  public BankAccountCardsResponseDTO(UUID id, String bankName, Double balance, UserMinimalVO user,
                                     List<CardMinimalVO> cards, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
    this.id = id;
    this.bankName = bankName;
    this.balance = balance;
    this.user = user;
    this.cards = cards;
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

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public UserMinimalVO getUser() {
    return user;
  }

  public void setUser(UserMinimalVO user) {
    this.user = user;
  }

  public List<CardMinimalVO> getCards() {
    return cards;
  }

  public void setCards(List<CardMinimalVO> cards) {
    this.cards = cards;
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
