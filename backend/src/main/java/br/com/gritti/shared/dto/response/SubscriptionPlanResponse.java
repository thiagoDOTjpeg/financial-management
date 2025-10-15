package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.BillingCycle;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Relation(collectionRelation = "subscription-plan")
public class SubscriptionPlanResponse extends RepresentationModel<SubscriptionPlanResponse> {
  private UUID id;
  private String name;
  private String displayName;
  private BigDecimal price;
  private BillingCycle billingCycle ;
  private Boolean hasAds ;
  private Integer maxBankAccounts;
  private Integer maxCards;
  private Boolean hasBudgets ;
  private Boolean hasGoals ;
  private Boolean hasReports ;
  private Boolean hasRecurringTransactions ;
  private Boolean isActive ;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public SubscriptionPlanResponse() {
  }

  public SubscriptionPlanResponse(UUID id, String name, String displayName, BigDecimal price, BillingCycle billingCycle,
                                  Boolean hasAds, Integer maxBankAccounts, Integer maxCards, Boolean hasBudgets, Boolean hasGoals,
                                  Boolean hasReports, Boolean hasRecurringTransactions, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.displayName = displayName;
    this.price = price;
    this.billingCycle = billingCycle;
    this.hasAds = hasAds;
    this.maxBankAccounts = maxBankAccounts;
    this.maxCards = maxCards;
    this.hasBudgets = hasBudgets;
    this.hasGoals = hasGoals;
    this.hasReports = hasReports;
    this.hasRecurringTransactions = hasRecurringTransactions;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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
