package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, UUID> {
  @Query("UPDATE SubscriptionPlan sp SET sp.isActive = false WHERE sp.id = :id")
  @Modifying
  void deactivatePlan(UUID id);
}
