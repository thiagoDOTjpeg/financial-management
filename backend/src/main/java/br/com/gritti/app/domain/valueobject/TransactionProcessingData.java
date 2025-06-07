package br.com.gritti.app.domain.valueobject;

import java.util.UUID;

public class TransactionProcessingData {
  private UUID cardId;
  private UUID fromAccountId;
  private UUID toAccountId;
  private InstallmentData installmentData;

  public TransactionProcessingData() {
  }

  public TransactionProcessingData(UUID cardId, UUID fromAccountId, UUID toAccountId, InstallmentData installmentData) {
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

}
