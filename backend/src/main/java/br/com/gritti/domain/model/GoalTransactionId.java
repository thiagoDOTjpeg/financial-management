package br.com.gritti.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class GoalTransactionId implements Serializable {

  @Column(name = "goal_id")
  private UUID goalId;

  @Column(name = "transaction_id")
  private UUID transactionId;

  public GoalTransactionId() {
  }

  public GoalTransactionId(UUID goalId, UUID transactionId) {
    this.goalId = goalId;
    this.transactionId = transactionId;
  }

  public UUID getGoalId() {
    return goalId;
  }

  public void setGoalId(UUID goalId) {
    this.goalId = goalId;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  // equals e hashCode (OBRIGATÓRIO para chave composta!)
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GoalTransactionId that)) return false;
    return Objects.equals(goalId, that.goalId) &&
            Objects.equals(transactionId, that.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(goalId, transactionId);
  }
}