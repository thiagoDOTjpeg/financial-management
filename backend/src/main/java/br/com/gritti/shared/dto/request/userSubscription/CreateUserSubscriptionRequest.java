package br.com.gritti.shared.dto.request.userSubscription;

import br.com.gritti.domain.enums.BillingCycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateUserSubscriptionRequest {
  private UUID planId;
  private BillingCycle billingCycle;

  public CreateUserSubscriptionRequest() {
  }

  public CreateUserSubscriptionRequest(UUID planId, BillingCycle billingCycle) {
    this.planId = planId;
    this.billingCycle = billingCycle;
  }

  public UUID getPlanId() {
    return planId;
  }

  public void setPlanId(UUID planId) {
    this.planId = planId;
  }

  public BillingCycle getBillingCycle() {
    return billingCycle;
  }

  public void setBillingCycle(BillingCycle billingCycle) {
    this.billingCycle = billingCycle;
  }
}
