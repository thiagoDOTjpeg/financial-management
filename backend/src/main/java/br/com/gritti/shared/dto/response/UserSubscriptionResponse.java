package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.SubscriptionStatus;
import br.com.gritti.domain.model.SubscriptionPlan;
import br.com.gritti.domain.model.User;
import br.com.gritti.domain.model.UserSubscription;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;

@Relation(collectionRelation = "user-subscription")
public class UserSubscriptionResponse extends RepresentationModel<UserSubscriptionResponse> {
  private UUID id;
  private UserResponse user;
  private SubscriptionPlanResponse plan;
  private SubscriptionStatus status;
  private LocalDateTime startedAt;
  private LocalDateTime expiresAt;
  private LocalDateTime cancelledAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserSubscriptionResponse() {
  }

  public UserSubscriptionResponse(UUID id, UserResponse user, SubscriptionPlanResponse plan, SubscriptionStatus status, LocalDateTime startedAt,
                                  LocalDateTime expiresAt, LocalDateTime cancelledAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.user = user;
    this.plan = plan;
    this.status = status;
    this.startedAt = startedAt;
    this.expiresAt = expiresAt;
    this.cancelledAt = cancelledAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserResponse getUser() {
    return user;
  }

  public void setUser(UserResponse user) {
    this.user = user;
  }

  public SubscriptionPlanResponse getPlan() {
    return plan;
  }

  public void setPlan(SubscriptionPlanResponse plan) {
    this.plan = plan;
  }

  public SubscriptionStatus getStatus() {
    return status;
  }

  public void setStatus(SubscriptionStatus status) {
    this.status = status;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public LocalDateTime getCancelledAt() {
    return cancelledAt;
  }

  public void setCancelledAt(LocalDateTime cancelledAt) {
    this.cancelledAt = cancelledAt;
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
