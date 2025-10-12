package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.GoalStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "financial_goals")
public class FinancialGoal extends AuditableEntity{
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "target_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal targetAmount;

  @Column
  private LocalDate deadline;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private GoalStatus status = GoalStatus.IN_PROGRESS;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @ManyToMany
  @JoinTable(
          name = "goal_transactions",
          joinColumns = @JoinColumn(name = "goal_id"),
          inverseJoinColumns = @JoinColumn(name = "transaction_id")
  )
  private Set<Transaction> contributions = new HashSet<>();

  public void markAsCompleted() {
    this.status = GoalStatus.COMPLETED;
    this.completedAt = LocalDateTime.now();
  }

  public void addContribution(Transaction transaction) {
    contributions.add(transaction);
  }

    public FinancialGoal() {
    }

    private FinancialGoal(FinancialGoal.Builder builder) {
      this.user = builder.user;
      this.name = builder.name;
      this.description = builder.description;
      this.targetAmount = builder.targetAmount;
      this.deadline = builder.deadline;
      this.status = builder.status;
      this.completedAt = builder.completedAt;
      this.contributions = builder.contributions;
    }

    public static class Builder {
      private User user;
      private String name;
      private String description;
      private BigDecimal targetAmount;
      private LocalDate deadline;
      private GoalStatus status = GoalStatus.IN_PROGRESS;
      private LocalDateTime completedAt;
      private Set<Transaction> contributions = new HashSet<>();

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder targetAmount(BigDecimal targetAmount) {
      this.targetAmount = targetAmount;
      return this;
    }

    public Builder deadline(LocalDate deadline) {
      this.deadline = deadline;
      return this;
    }

    public Builder status(GoalStatus status) {
      this.status = status;
      return this;
    }

    public Builder completedAt(LocalDateTime completedAt) {
      this.completedAt = completedAt;
      return this;
    }

    public Builder contributions(Set<Transaction> contributions) {
      this.contributions = contributions;
      return this;
    }

    public FinancialGoal build() {
      return new FinancialGoal(this);
    }
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getTargetAmount() {
    return targetAmount;
  }

  public void setTargetAmount(BigDecimal targetAmount) {
    this.targetAmount = targetAmount;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public GoalStatus getStatus() {
    return status;
  }

  public void setStatus(GoalStatus status) {
    this.status = status;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(LocalDateTime completedAt) {
    this.completedAt = completedAt;
  }

  public Set<Transaction> getContributions() {
    return contributions;
  }

  public void setContributions(Set<Transaction> contributions) {
    this.contributions = contributions;
  }
}
