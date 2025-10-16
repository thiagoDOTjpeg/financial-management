package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.UserSubscriptionServiceImpl;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/subscription")
public class UserSubscriptionController {
  private final UserSubscriptionServiceImpl userSubscriptionServiceImpl;

  @Autowired
  public UserSubscriptionController(UserSubscriptionServiceImpl userSubscriptionServiceImpl) {
    this.userSubscriptionServiceImpl = userSubscriptionServiceImpl;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserSubscriptionResponse> createSubscription(@AuthenticatedUserId UUID userId, @Valid @RequestBody CreateUserSubscriptionRequest request) {
    UserSubscriptionResponse userSubscriptionResponse = userSubscriptionServiceImpl.createUserSubscription(userId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(userSubscriptionResponse);
  }

  @GetMapping(value = "/my-subscription", produces =  MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserSubscriptionResponse> getUserSubscriptionById(@AuthenticatedUserId UUID userId) {
    UserSubscriptionResponse response = userSubscriptionServiceImpl.getUserSubscriptionById(userId);
    return ResponseEntity.ok(response);
  }

  @PatchMapping(value = "/cancel", produces =  MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> cancelSubscription(@RequestParam UUID userId) {
    userSubscriptionServiceImpl.cancelUserSubscription(userId);
    return ResponseEntity.noContent().build();
  }
}
