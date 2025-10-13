package br.com.gritti.domain.repository;

import br.com.gritti.domain.model.User;
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

  UUID user(User user);
}
