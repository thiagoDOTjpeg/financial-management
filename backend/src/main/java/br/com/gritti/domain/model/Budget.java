package br.com.gritti.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class Budget extends AuditableEntity{
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(name = "budget_month", nullable = false)
  private LocalDate budgetMonth;

  @Column(name = "planned_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal plannedAmount;

  @Column(name = "alert_threshold")
  private Integer alertThreshold = 80;

  @Column(name = "alert_sent", nullable = false)
  private Boolean alertSent = false;

  @PrePersist
  @PreUpdate
  private void validate() {
    if (alertThreshold != null && (alertThreshold < 0 || alertThreshold > 100)) {
      throw new IllegalArgumentException("Alert threshold must be between 0 and 100");
    }
  }

  public Budget() {
  }

  private Budget(Builder builder) {
    this.user = builder.user;
    this.category = builder.category;
    this.budgetMonth = builder.budgetMonth;
    this.plannedAmount = builder.plannedAmount;
    this.alertThreshold = builder.alertThreshold;
    this.alertSent = builder.alertSent;
  }

  public static class Builder {
    private User user;
    private Category category;
    private LocalDate budgetMonth;
    private BigDecimal plannedAmount;
    private Integer alertThreshold;
    private Boolean alertSent;

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder category(Category category) {
      this.category = category;
      return this;
    }

    public Builder budgetMonth(LocalDate budgetMonth) {
      this.budgetMonth = budgetMonth;
      return this;
    }

    public Builder plannedAmount(BigDecimal plannedAmount) {
      this.plannedAmount = plannedAmount;
      return this;
    }

    public Builder alertThreshold(Integer alertThreshold) {
      this.alertThreshold = alertThreshold;
      return this;
    }

    public Builder alertSent(Boolean alertSent) {
      this.alertSent = alertSent;
      return this;
    }

    public Budget build() {
      return new Budget(this);

    }
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public LocalDate getBudgetMonth() {
    return budgetMonth;
  }

  public void setBudgetMonth(LocalDate budgetMonth) {
    this.budgetMonth = budgetMonth;
  }

  public BigDecimal getPlannedAmount() {
    return plannedAmount;
  }

  public void setPlannedAmount(BigDecimal plannedAmount) {
    this.plannedAmount = plannedAmount;
  }

  public Integer getAlertThreshold() {
    return alertThreshold;
  }

  public void setAlertThreshold(Integer alertThreshold) {
    this.alertThreshold = alertThreshold;
  }

  public Boolean getAlertSent() {
    return alertSent;
  }

  public void setAlertSent(Boolean alertSent) {
    this.alertSent = alertSent;
  }
}
