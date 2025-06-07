package br.com.gritti.app.application.dto.transaction;

import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class TransactionCreateDTO {
  @NotNull(message = "Timestamp cannot be null")
  private Date timestamp = new Date();

  @Min(message = "Value cannot be negative", value = 0)
  private Double value;

  @NotNull(message = "Payment type cannot be null")
  private PaymentType paymentType;

  @NotNull(message = "Category type cannot be null")
  private Category category;

  @Min(message = "Number installment cannot be negative", value = 0)
  private Integer numberInstallment;

  @Min(message = "Installment value cannot be negative", value = 0)
  private Double installmentValue;

  private UUID fromAccountId;

  private UUID toAccountId;

  public TransactionCreateDTO() {
  }

  public TransactionCreateDTO(Date timestamp, Double value, PaymentType paymentType, Category category, Integer numberInstallment, Double installmentValue, UUID fromAccountId, UUID toAccountId) {
    this.timestamp = timestamp;
    this.value = value;
    this.paymentType = paymentType;
    this.category = category;
    this.numberInstallment = numberInstallment;
    this.installmentValue = installmentValue;
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Integer getNumberInstallment() {
    return numberInstallment;
  }

  public void setNumberInstallment(Integer numberInstallment) {
    this.numberInstallment = numberInstallment;
  }

  public Double getInstallmentValue() {
    return installmentValue;
  }

  public void setInstallmentValue(Double installmentValue) {
    this.installmentValue = installmentValue;
  }

  public UUID getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(UUID fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public UUID getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(UUID toAccountId) {
    this.toAccountId = toAccountId;
  }
}
