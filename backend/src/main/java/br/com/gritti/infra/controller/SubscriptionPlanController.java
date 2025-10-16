package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.SubscriptionPlanServiceImpl;
import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.request.subscriptionPlan.UpdateSubscriptionPlan;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plans")
public class SubscriptionPlanController {
  private final SubscriptionPlanServiceImpl subscriptionPlanServiceImpl;

  @Autowired
  public SubscriptionPlanController(SubscriptionPlanServiceImpl subscriptionPlanServiceImpl) {
    this.subscriptionPlanServiceImpl = subscriptionPlanServiceImpl;
  }

  @GetMapping()
  public ResponseEntity<PagedModel<EntityModel<SubscriptionPlanResponse>>> getSubscriptionPlans(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          PagedResourcesAssembler<SubscriptionPlanResponse> pagedResourcesAssembler
  ){
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    PagedModel<EntityModel<SubscriptionPlanResponse>> response = pagedResourcesAssembler.toModel(subscriptionPlanServiceImpl.getAll(pageable));
    return ResponseEntity.ok(response);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SubscriptionPlanResponse> createSubscriptionPlan(@Valid @RequestBody CreateSubscriptionPlanRequest request) {
    SubscriptionPlanResponse response =  subscriptionPlanServiceImpl.createSubscriptionPlan(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping(value = "/{planId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SubscriptionPlanResponse> updatePlan(@PathVariable UUID planId, @Valid @RequestBody UpdateSubscriptionPlan request) {
    SubscriptionPlanResponse response =  subscriptionPlanServiceImpl.updateSubscriptionPlan(planId, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping(value = "/{planId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deactivatePlan(@PathVariable UUID planId) {
    subscriptionPlanServiceImpl.deactivatePlan(planId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
