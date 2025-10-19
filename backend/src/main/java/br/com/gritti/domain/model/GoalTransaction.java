package br.com.gritti.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goal_transactions")
public class GoalTransaction {

  @EmbeddedId
  private GoalTransactionId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("goalId")
  @JoinColumn(name = "goal_id", nullable = false)
  private FinancialGoal goal;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("transactionId")
  @JoinColumn(name = "transaction_id", nullable = false)
  private Transaction transaction;

  @Column(name = "contributed_at", nullable = false)
  private LocalDateTime contributedAt;

  // Construtores
  public GoalTransaction() {
    this.contributedAt = LocalDateTime.now();
  }

  public GoalTransaction(UUID goalId, UUID transactionId) {
    this.id = new GoalTransactionId(goalId, transactionId);
    this.contributedAt = LocalDateTime.now();
  }

  public GoalTransaction(FinancialGoal goal, Transaction transaction) {
    this.goal = goal;
    this.transaction = transaction;
    this.id = new GoalTransactionId(goal.getId(), transaction.getId());
    this.contributedAt = LocalDateTime.now();
  }

  // Getters e Setters
  public GoalTransactionId getId() {
    return id;
  }

  public void setId(GoalTransactionId id) {
    this.id = id;
  }

  public FinancialGoal getGoal() {
    return goal;
  }

  public void setGoal(FinancialGoal goal) {
    this.goal = goal;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;
  }

  public LocalDateTime getContributedAt() {
    return contributedAt;
  }

  public void setContributedAt(LocalDateTime contributedAt) {
    this.contributedAt = contributedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GoalTransaction)) return false;
    GoalTransaction that = (GoalTransaction) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
