package br.com.gritti.infra.controller;

import br.com.gritti.application.service.UserSubscriptionService;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/subscription")
public class UserSubscriptionController {
  private UserSubscriptionService userSubscriptionService;

  @Autowired
  public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
    this.userSubscriptionService = userSubscriptionService;
  }

  @PostMapping
  public ResponseEntity<UserSubscriptionResponse> createSubscription(@Valid @RequestBody CreateUserSubscriptionRequest request) {
    //TODO
    return null;
  }

  @GetMapping("/my-subscription")
  public ResponseEntity<UserSubscriptionResponse> getUserSubscriptionById() {
    //TODO
    return null;
  }

  @PutMapping("/cancel")
  public ResponseEntity<Void> cancelSubscription() {
    //TODO
    return null;
  }
}
