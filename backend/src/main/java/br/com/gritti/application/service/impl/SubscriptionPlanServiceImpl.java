package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.SubscriptionPlanService;
import br.com.gritti.domain.model.SubscriptionPlan;
import br.com.gritti.domain.repository.SubscriptionPlanRepository;
import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.request.subscriptionPlan.UpdateSubscriptionPlan;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.SubscriptionPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
  private final SubscriptionPlanRepository subscriptionPlanRepository;

  @Autowired
  public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
    this.subscriptionPlanRepository = subscriptionPlanRepository;
  }

  @Override
  public SubscriptionPlanResponse getById(UUID id) {
    SubscriptionPlan response = subscriptionPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));
    return SubscriptionPlanMapper.toResponse(response);
  }

  @Override
  public Page<SubscriptionPlanResponse> getAll(Pageable pageable) {
    Page<SubscriptionPlan> response = subscriptionPlanRepository.findAll(pageable);
    return response.map(SubscriptionPlanMapper::toResponse);
  }

  @Override
  public SubscriptionPlanResponse updateSubscriptionPlan(UUID id, UpdateSubscriptionPlan request) {
    SubscriptionPlan oldPlan = subscriptionPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));
    oldPlan.setActive(false);
    oldPlan.setUpdatedAt(LocalDateTime.now());
    subscriptionPlanRepository.save(oldPlan);
    SubscriptionPlan newPlan = new SubscriptionPlan(oldPlan);

    newPlan.setName(request.name());
    newPlan.setDisplayName(request.displayName());
    newPlan.setPrice(request.price());
    newPlan.setBillingCycle(request.billingCycle());
    newPlan.setHasAds(request.hasAds());
    newPlan.setMaxBankAccounts(request.maxBankAccounts());
    newPlan.setMaxCards(request.maxCards());
    newPlan.setHasBudgets(request.hasBudgets());
    newPlan.setHasGoals(request.hasGoals());
    newPlan.setHasReports(request.hasReports());
    newPlan.setHasRecurringTransactions(request.hasRecurringTransactions());

    return SubscriptionPlanMapper.toResponse(subscriptionPlanRepository.save(newPlan));
  }

  @Override
  public SubscriptionPlanResponse createSubscriptionPlan(CreateSubscriptionPlanRequest request) {
    SubscriptionPlan response = subscriptionPlanRepository.save(SubscriptionPlanMapper.toEntity(request));
    return SubscriptionPlanMapper.toResponse(response);
  }

  @Override
  public void deactivatePlan(UUID id) {
    subscriptionPlanRepository.deactivatePlan(id);
  }
}
