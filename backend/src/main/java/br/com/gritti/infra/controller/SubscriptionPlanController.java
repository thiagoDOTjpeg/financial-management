package br.com.gritti.infra.controller;

import br.com.gritti.application.service.impl.SubscriptionPlanServiceImpl;
import br.com.gritti.infra.controller.contract.SubscriptionPlanApi;
import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.request.subscriptionPlan.UpdateSubscriptionPlan;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SubscriptionPlanController implements SubscriptionPlanApi {
  private final SubscriptionPlanServiceImpl subscriptionPlanServiceImpl;

  @Autowired
  public SubscriptionPlanController(SubscriptionPlanServiceImpl subscriptionPlanServiceImpl) {
    this.subscriptionPlanServiceImpl = subscriptionPlanServiceImpl;
  }

  @Override
  public ResponseEntity<PagedModel<EntityModel<SubscriptionPlanResponse>>> getSubscriptionPlans(
          Integer page, Integer size, String direction, PagedResourcesAssembler<SubscriptionPlanResponse> pagedResourcesAssembler
  ){
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    PagedModel<EntityModel<SubscriptionPlanResponse>> response = pagedResourcesAssembler.toModel(subscriptionPlanServiceImpl.getAll(pageable));
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<SubscriptionPlanResponse> createSubscriptionPlan(CreateSubscriptionPlanRequest request) {
    SubscriptionPlanResponse response =  subscriptionPlanServiceImpl.createSubscriptionPlan(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  public ResponseEntity<SubscriptionPlanResponse> updatePlan(UUID planId, UpdateSubscriptionPlan request) {
    SubscriptionPlanResponse response =  subscriptionPlanServiceImpl.updateSubscriptionPlan(planId, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public ResponseEntity<Void> deactivatePlan(UUID planId) {
    subscriptionPlanServiceImpl.deactivatePlan(planId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
