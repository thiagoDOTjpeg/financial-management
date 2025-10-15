package br.com.gritti.application.service;

import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.request.subscriptionPlan.UpdateSubscriptionPlan;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubscriptionPlanService {
  SubscriptionPlanResponse getById(UUID id);
  Page<SubscriptionPlanResponse> getAll(Pageable pageable);
  SubscriptionPlanResponse updateTransaction(UUID id, UpdateSubscriptionPlan request);
  SubscriptionPlanResponse createSubscriptionPlan(CreateSubscriptionPlanRequest request);
  void deactivePlan(UUID id);
}
