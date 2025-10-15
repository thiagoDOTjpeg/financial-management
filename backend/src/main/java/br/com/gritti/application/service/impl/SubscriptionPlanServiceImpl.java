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
  public SubscriptionPlanResponse updateTransaction(UUID id, UpdateSubscriptionPlan request) {
    SubscriptionPlan response = subscriptionPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));
    response.setName(request.name());
    response.setDisplayName(request.displayName());
    response.setPrice(request.price());
    response.setBillingCycle(request.billingCycle());
    response.setHasAds(request.hasAds());
    response.setMaxBankAccounts(request.maxBankAccounts());
    response.setMaxCards(request.maxCards());
    response.setHasBudgets(request.hasBudgets());
    response.setHasGoals(request.hasGoals());
    response.setHasReports(request.hasReports());
    response.setHasRecurringTransactions(request.hasRecurringTransactions());
    return SubscriptionPlanMapper.toResponse(subscriptionPlanRepository.save(response));
  }

  @Override
  public SubscriptionPlanResponse createSubscriptionPlan(CreateSubscriptionPlanRequest request) {
    SubscriptionPlan response = subscriptionPlanRepository.save(SubscriptionPlanMapper.toEntity(request));
    return SubscriptionPlanMapper.toResponse(response);
  }

  @Override
  public void deactivePlan(UUID id) {
    subscriptionPlanRepository.deactivatePlan(id);
  }
}
