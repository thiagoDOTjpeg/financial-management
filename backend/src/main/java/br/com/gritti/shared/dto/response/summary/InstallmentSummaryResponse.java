package br.com.gritti.shared.dto.response.summary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class InstallmentSummaryResponse {
  private UUID id;
  private String description;
  private BigDecimal totalAmount;
  private Integer totalInstallments;
  private BigDecimal installmentValue;
  private LocalDate firstDueDate;

  public InstallmentSummaryResponse() {
  }

  public InstallmentSummaryResponse(UUID id, String description, BigDecimal totalAmount, Integer totalInstallments,
                                    BigDecimal installmentValue, LocalDate firstDueDate) {
    this.id = id;
    this.description = description;
    this.totalAmount = totalAmount;
    this.totalInstallments = totalInstallments;
    this.installmentValue = installmentValue;
    this.firstDueDate = firstDueDate;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
}

