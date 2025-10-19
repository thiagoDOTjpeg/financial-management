package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.model.Card;
import br.com.gritti.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class InstallmentResponse {
  private UUID id;
  private Card card;
  private String description;
  private BigDecimal totalAmount;
  private Integer totalInstallments;
  private BigDecimal installmentValue;
  private LocalDate firstDueDate;
  private Set<Transaction> transactions;

  public InstallmentResponse() {
  }

  public InstallmentResponse(UUID id, Card card, String description, BigDecimal totalAmount, Integer totalInstallments,
                             BigDecimal installmentValue, LocalDate firstDueDate, Set<Transaction> transactions) {
    this.id = id;
    this.card = card;
    this.description = description;
    this.totalAmount = totalAmount;
    this.totalInstallments = totalInstallments;
    this.installmentValue = installmentValue;
    this.firstDueDate = firstDueDate;
    this.transactions = transactions;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Integer getTotalInstallments() {
    return totalInstallments;
  }

  public void setTotalInstallments(Integer totalInstallments) {
    this.totalInstallments = totalInstallments;
  }

  public BigDecimal getInstallmentValue() {
    return installmentValue;
  }

  public void setInstallmentValue(BigDecimal installmentValue) {
    this.installmentValue = installmentValue;
  }

  public LocalDate getFirstDueDate() {
    return firstDueDate;
  }

  public void setFirstDueDate(LocalDate firstDueDate) {
    this.firstDueDate = firstDueDate;
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }
}
