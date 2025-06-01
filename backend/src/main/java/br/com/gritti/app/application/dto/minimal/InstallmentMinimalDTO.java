package br.com.gritti.app.application.dto.minimal;

import java.util.UUID;

public class InstallmentMinimalDTO {
  private UUID id;
  private Integer numberInstallment;
  private Double installmentValue;

  public InstallmentMinimalDTO() {
  }

  public InstallmentMinimalDTO(UUID id, Integer numberInstallment, Double installmentValue) {
    this.id = id;
    this.numberInstallment = numberInstallment;
    this.installmentValue = installmentValue;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
}
