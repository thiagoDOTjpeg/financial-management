package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.SubscriptionPlan;
import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;

public class SubscriptionPlanMapper {
  public SubscriptionPlanMapper() {
  }

  public static SubscriptionPlan toEntity(CreateSubscriptionPlanRequest request) {
    return new SubscriptionPlan.Builder()
            .name(request.name())
            .displayName(request.displayName())
            .price(request.price())
            .billingCycle(request.billingCycle())
            .hasAds(request.hasAds())
            .maxBankAccounts(request.maxBankAccounts())
            .maxCards(request.maxCards())
            .hasBudgets(request.hasBudgets())
            .hasGoals(request.hasGoals())
            .hasReports(request.hasReports())
            .hasRecurringTransactions(request.hasRecurringTransactions())
            .build();
  }

  public static SubscriptionPlanResponse toResponse(SubscriptionPlan entity) {
    SubscriptionPlanResponse response = new SubscriptionPlanResponse();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setDisplayName(entity.getDisplayName());
    response.setPrice(entity.getPrice());
    response.setBillingCycle(entity.getBillingCycle());
    response.setHasAds(entity.getHasAds());
    response.setMaxBankAccounts(entity.getMaxBankAccounts());
    response.setMaxCards(entity.getMaxCards());
    response.setHasBudgets(entity.getHasBudgets());
    response.setHasGoals(entity.getHasGoals());
    response.setHasReports(entity.getHasReports());
    response.setHasRecurringTransactions(entity.getHasRecurringTransactions());
    response.setActive(entity.getActive());
    response.setCreatedAt(entity.getCreatedAt());
    response.setUpdatedAt(entity.getUpdatedAt());
    return response;
  }
}
