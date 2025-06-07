package br.com.gritti.app.domain.valueobject;

import java.util.Date;

public class InstallmentData {
  private Integer numberInstallment;
  private Double installmentValue;
  private Date dueDate;

  public InstallmentData() {
  }

  public InstallmentData(Integer numberInstallment, Double installmentValue, Date dueDate) {
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
