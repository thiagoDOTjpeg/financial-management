package br.com.gritti.shared.dto.request.userSubscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateUserSubscriptionRequest {
  private String planId;
  private LocalDateTime expiresAt;

  public CreateUserSubscriptionRequest() {
  }

  public CreateUserSubscriptionRequest(String planId, LocalDateTime expiresAt) {
    this.planId = planId;
    this.expiresAt = expiresAt;
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
}
