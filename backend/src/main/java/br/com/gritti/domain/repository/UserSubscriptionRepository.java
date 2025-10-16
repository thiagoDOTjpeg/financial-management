package br.com.gritti.domain.repository;

import br.com.gritti.domain.enums.SubscriptionStatus;
import br.com.gritti.domain.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UUID> {

  @Modifying
  @Query("UPDATE UserSubscription us SET us.cancelledAt = CURRENT_TIMESTAMP WHERE us.user.id = :userId")
  void cancelUserSubscription(UUID userId);

  @Query("SELECT us FROM UserSubscription us WHERE us.user.id = :userId")
  Optional<UserSubscription> getUserSubscriptionByUserId(UUID userId);

  @Query("SELECT CASE WHEN COUNT(us) > 0 THEN TRUE ELSE FALSE END FROM UserSubscription us WHERE us.user.id = :userId")
  Boolean verifyUserSubscription(UUID userId);

  @Query("SELECT us FROM UserSubscription us JOIN FETCH us.user u JOIN FETCH us.plan WHERE us.user.id = :userId AND us.status = :status")
  Optional<UserSubscription> findActiveSubscriptionByUserIdWithDetails(UUID userId, SubscriptionStatus status);

}
