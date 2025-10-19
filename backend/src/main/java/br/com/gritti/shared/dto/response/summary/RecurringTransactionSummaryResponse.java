package br.com.gritti.shared.dto.response.summary;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.RecurringFrequency;
import br.com.gritti.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RecurringTransactionSummaryResponse {
  private UUID id;
  private String description;
  private BigDecimal amount;
  private RecurringFrequency frequency;
  private Integer dayOfMonth;
  private Integer dayOfWeek;
  private PaymentType paymentType;
  private LocalDate startDate;
  private LocalDate endDate;
  private Boolean isActive;

  public RecurringTransactionSummaryResponse() {
  }

  public RecurringTransactionSummaryResponse(UUID id, String description, BigDecimal amount, RecurringFrequency frequency,
                                             Integer dayOfMonth, Integer dayOfWeek, PaymentType paymentType, LocalDate startDate, LocalDate endDate, Boolean isActive) {
    this.id = id;
    this.description = description;
    this.amount = amount;
    this.frequency = frequency;
    this.dayOfMonth = dayOfMonth;
    this.dayOfWeek = dayOfWeek;
    this.paymentType = paymentType;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isActive = isActive;
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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public RecurringFrequency getFrequency() {
    return frequency;
  }

  public void setFrequency(RecurringFrequency frequency) {
    this.frequency = frequency;
  }

  public Integer getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Integer getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(Integer dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
