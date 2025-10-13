package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.UserSubscription;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;

public class UserSubscriptionMapper {
  public UserSubscriptionMapper() {
  }

  public static UserSubscription toEntity(CreateUserSubscriptionRequest request) {
    return new  UserSubscription.Builder()
            .expiresAt(request.getExpiresAt())
            .build();
  }

  public static UserSubscriptionResponse toResponse(UserSubscription userSubscription) {
    UserSubscriptionResponse userSubscriptionResponse = new UserSubscriptionResponse();
    userSubscriptionResponse.setId(userSubscription.getId());
    userSubscriptionResponse.setUser(userSubscription.getUser());
    userSubscriptionResponse.setPlan(userSubscription.getPlan());
    userSubscriptionResponse.setStatus(userSubscription.getStatus());
    userSubscriptionResponse.setStartedAt(userSubscription.getStartedAt());
    userSubscriptionResponse.setExpiresAt(userSubscription.getExpiresAt());
    userSubscriptionResponse.setCancelledAt(userSubscription.getCancelledAt());
    userSubscriptionResponse.setCreatedAt(userSubscription.getCreatedAt());
    userSubscriptionResponse.setUpdatedAt(userSubscription.getUpdatedAt());
    return userSubscriptionResponse;
  }
}
