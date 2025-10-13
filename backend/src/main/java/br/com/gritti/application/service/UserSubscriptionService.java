package br.com.gritti.application.service;

import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionService {
  UserSubscriptionResponse createUserSubscription(CreateUserSubscriptionRequest request);
  void cancelUserSubscription(UUID userId);
  UserSubscriptionResponse getUserSubscriptionById(UUID userId);
  PagedModel<EntityModel<UserSubscriptionResponse>> getAllUserSubscriptions(PageRequest pageable);
}
