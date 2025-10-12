package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.BillingCycle;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "display_name", nullable = false, length = 100)
  private String displayName;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price = BigDecimal.ZERO;

  @Enumerated(EnumType.STRING)
  @Column(name = "billing_cycle", nullable = false, length = 20)
  private BillingCycle billingCycle = BillingCycle.MONTHLY;

  @Column(name = "has_ads", nullable = false)
  private Boolean hasAds = true;

  @Column(name = "max_bank_accounts")
  private Integer maxBankAccounts;

  @Column(name = "max_cards")
  private Integer maxCards;

  @Column(name = "has_budgets", nullable = false)
  private Boolean hasBudgets = false;

  @Column(name = "has_goals", nullable = false)
  private Boolean hasGoals = false;

  @Column(name = "has_reports", nullable = false)
  private Boolean hasReports = false;

  @Column(name = "has_recurring_transactions", nullable = false)
  private Boolean hasRecurringTransactions = false;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public SubscriptionPlan() {
  }

  private SubscriptionPlan(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.displayName = builder.displayName;
    this.price = builder.price;
    this.billingCycle = builder.billingCycle;
    this.hasAds = builder.hasAds;
    this.maxBankAccounts = builder.maxBankAccounts;
    this.maxCards = builder.maxCards;
    this.hasBudgets = builder.hasBudgets;
    this.hasGoals = builder.hasGoals;
    this.hasReports = builder.hasReports;
    this.hasRecurringTransactions = builder.hasRecurringTransactions;
    this.isActive = builder.isActive;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  public static class Builder {
    private UUID id;
    private String name;
    private String displayName;
    private BigDecimal price;
    private BillingCycle billingCycle;
    private Boolean hasAds;
    private Integer maxBankAccounts;
    private Integer maxCards;
    private Boolean hasBudgets;
    private Boolean hasGoals;
    private Boolean hasReports;
    private Boolean hasRecurringTransactions;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
    public Builder displayName(String displayName) {
      this.displayName = displayName;
      return this;
    }

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder billingCycle(BillingCycle billingCycle) {
      this.billingCycle = billingCycle;
      return this;
    }

    public Builder hasAds(Boolean hasAds) {
      this.hasAds = hasAds;
      return this;
    }

    public Builder maxBankAccounts(Integer maxBankAccounts) {
      this.maxBankAccounts = maxBankAccounts;
      return this;
    }

    public Builder maxCards(Integer maxCards) {
      this.maxCards = maxCards;
      return this;
    }

    public Builder hasBudgets(Boolean hasBudgets) {
      this.hasBudgets = hasBudgets;
      return this;
    }

    public Builder hasGoals(Boolean hasGoals) {
      this.hasGoals = hasGoals;
      return this;
    }

    public Builder hasReports(Boolean hasReports) {
      this.hasReports = hasReports;
      return this;
    }

    public Builder hasRecurringTransactions(Boolean hasRecurringTransactions) {
      this.hasRecurringTransactions = hasRecurringTransactions;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public SubscriptionPlan build() {
      return new SubscriptionPlan(this);
    }
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BillingCycle getBillingCycle() {
    return billingCycle;
  }

  public void setBillingCycle(BillingCycle billingCycle) {
    this.billingCycle = billingCycle;
  }

  public Boolean getHasAds() {
    return hasAds;
  }

  public void setHasAds(Boolean hasAds) {
    this.hasAds = hasAds;
  }

  public Integer getMaxBankAccounts() {
    return maxBankAccounts;
  }

  public void setMaxBankAccounts(Integer maxBankAccounts) {
    this.maxBankAccounts = maxBankAccounts;
  }

  public Integer getMaxCards() {
    return maxCards;
  }

  public void setMaxCards(Integer maxCards) {
    this.maxCards = maxCards;
  }

  public Boolean getHasBudgets() {
    return hasBudgets;
  }

  public void setHasBudgets(Boolean hasBudgets) {
    this.hasBudgets = hasBudgets;
  }

  public Boolean getHasGoals() {
    return hasGoals;
  }

  public void setHasGoals(Boolean hasGoals) {
    this.hasGoals = hasGoals;
  }

  public Boolean getHasReports() {
    return hasReports;
  }

  public void setHasReports(Boolean hasReports) {
    this.hasReports = hasReports;
  }

  public Boolean getHasRecurringTransactions() {
    return hasRecurringTransactions;
  }

  public void setHasRecurringTransactions(Boolean hasRecurringTransactions) {
    this.hasRecurringTransactions = hasRecurringTransactions;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
