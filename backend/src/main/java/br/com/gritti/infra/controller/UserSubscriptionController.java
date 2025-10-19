package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.UserSubscriptionServiceImpl;
import br.com.gritti.infra.controller.contract.UserSubscriptionApi;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserSubscriptionController implements UserSubscriptionApi {
  private final UserSubscriptionServiceImpl userSubscriptionServiceImpl;

  @Autowired
  public UserSubscriptionController(UserSubscriptionServiceImpl userSubscriptionServiceImpl) {
    this.userSubscriptionServiceImpl = userSubscriptionServiceImpl;
  }

  @Override
  public ResponseEntity<UserSubscriptionResponse> createSubscription(@AuthenticatedUserId UUID userId, CreateUserSubscriptionRequest request) {
    UserSubscriptionResponse userSubscriptionResponse = userSubscriptionServiceImpl.createUserSubscription(userId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(userSubscriptionResponse);
  }

  @Override
  public ResponseEntity<UserSubscriptionResponse> getUserSubscriptionById(@AuthenticatedUserId UUID userId) {
    UserSubscriptionResponse response = userSubscriptionServiceImpl.getUserSubscriptionById(userId);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> cancelSubscription(UUID userId) {
    userSubscriptionServiceImpl.cancelUserSubscription(userId);
    return ResponseEntity.noContent().build();
  }
}
