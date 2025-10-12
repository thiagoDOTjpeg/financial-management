package br.com.gritti.domain.model;


import br.com.gritti.domain.enums.SubscriptionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_subscriptions")
public class UserSubscription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id", nullable = false)
  private SubscriptionPlan plan;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

  @Column(name = "started_at", nullable = false)
  private LocalDateTime startedAt = LocalDateTime.now();

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public boolean isActive() {
    return status == SubscriptionStatus.ACTIVE &&
            (expiresAt == null || expiresAt.isAfter(LocalDateTime.now()));
  }

  public UserSubscription() {
  }

  private UserSubscription(Builder builder) {
    this.id = builder.id;
    this.user = builder.user;
    this.plan = builder.plan;
    this.status = builder.status;
    this.startedAt = builder.startedAt;
    this.expiresAt = builder.expiresAt;
    this.cancelledAt = builder.cancelledAt;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  public static class Builder {
    private UUID id;
    private User user;
    private SubscriptionPlan plan;
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;
    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime expiresAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }
    public Builder user(User user) {
      this.user = user;
      return this;
    }
    public Builder plan(SubscriptionPlan plan) {
      this.plan = plan;
      return this;
    }
    public Builder status(SubscriptionStatus status) {
      this.status = status;
      return this;
    }
    public Builder startedAt(LocalDateTime startedAt) {
      this.startedAt = startedAt;
      return this;
    }
    public Builder expiresAt(LocalDateTime expiresAt) {
      this.expiresAt = expiresAt;
      return this;
    }
    public Builder cancelledAt(LocalDateTime cancelledAt) {
      this.cancelledAt = cancelledAt;
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
    public UserSubscription build() {
      return new UserSubscription(this);
    }
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public SubscriptionPlan getPlan() {
    return plan;
  }

  public void setPlan(SubscriptionPlan plan) {
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
