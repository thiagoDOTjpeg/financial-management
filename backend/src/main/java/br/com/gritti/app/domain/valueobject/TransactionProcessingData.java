package br.com.gritti.app.domain.valueobject;

import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.model.Category;

import java.util.Date;
import java.util.UUID;

public class TransactionProcessingData {
  private Date timestamp = new Date();
  private Double value;
  private PaymentType paymentType;
  private Category category;
  private UUID cardId;
  private UUID fromAccountId;
  private UUID toAccountId;
  private InstallmentData installmentData;

  public TransactionProcessingData() {
  }

  public TransactionProcessingData(Date timestamp, Double value, PaymentType paymentType, Category category,
                                   UUID cardId, UUID fromAccountId, UUID toAccountId, InstallmentData installmentData) {
    this.timestamp = timestamp;
    this.value = value;
    this.paymentType = paymentType;
    this.category = category;
    this.cardId = cardId;
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
    this.installmentData = installmentData;
  }

  public UUID getCardId() {
    return cardId;
  }

  public void setCardId(UUID cardId) {
    this.cardId = cardId;
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

  public InstallmentData getInstallmentData() {
    return installmentData;
  }

  public void setInstallmentData(InstallmentData installmentData) {
    this.installmentData = installmentData;
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
}
