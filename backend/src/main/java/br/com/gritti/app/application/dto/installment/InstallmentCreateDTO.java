package br.com.gritti.app.application.dto.installment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class InstallmentCreateDTO {
  @Min(message = "Number installment cannot be negative", value = 0)
  private Integer numberInstallment;
  @Min(message = "Installment value cannot be negative", value = 0)
  private Double installmentValue;
  @NotNull(message = "Due date cannot be null")
  private Date dueDate;

  public InstallmentCreateDTO() {
  }

  public InstallmentCreateDTO(Integer numberInstallment, Double installmentValue, Date dueDate) {
    this.numberInstallment = numberInstallment;
    this.installmentValue = installmentValue;
    this.dueDate = dueDate;
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

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }
}
