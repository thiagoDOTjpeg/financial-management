package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.UserSubscriptionService;
import br.com.gritti.domain.enums.SubscriptionStatus;
import br.com.gritti.domain.model.SubscriptionPlan;
import br.com.gritti.domain.model.User;
import br.com.gritti.domain.model.UserSubscription;
import br.com.gritti.domain.repository.SubscriptionPlanRepository;
import br.com.gritti.domain.repository.UserRepository;
import br.com.gritti.domain.repository.UserSubscriptionRepository;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import br.com.gritti.shared.exception.BusinessException;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.UserSubscriptionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

  private final UserSubscriptionRepository userSubscriptionRepository;
  private final UserRepository userRepository;
  private final SubscriptionPlanRepository subscriptionPlanRepository;

  @Autowired
  public UserSubscriptionServiceImpl(UserSubscriptionRepository userSubscriptionRepository,  SubscriptionPlanRepository subscriptionPlanRepository,  UserRepository userRepository) {
    this.userSubscriptionRepository = userSubscriptionRepository;
    this.subscriptionPlanRepository = subscriptionPlanRepository;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserSubscriptionResponse createUserSubscription(UUID userId, CreateUserSubscriptionRequest request) {
    SubscriptionPlan plan = subscriptionPlanRepository.findByIdActivePlan(request.planId()).orElseThrow(() -> new ResourceNotFoundException("Nenhum plano ativo foi encontrado"));
    User user = userRepository.findByIdWithRoles(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    Optional<UserSubscription> existingUserSubscription = userSubscriptionRepository.findActiveSubscriptionByUserIdWithDetails(userId, SubscriptionStatus.ACTIVE);
    existingUserSubscription.ifPresent(existingUserSubscription1 -> {
      if (existingUserSubscription1.getPlan().getId().equals(plan.getId())) {
        throw new BusinessException("O usuário já possui este plano ativo");
      }
      existingUserSubscription1.setStatus(SubscriptionStatus.CANCELLED);
      existingUserSubscription1.setCreatedAt(LocalDateTime.now());
      userSubscriptionRepository.save(existingUserSubscription1);
    });

    LocalDateTime expireDate = calculateBillingCycle(plan);
    UserSubscription newUserSubscription = new UserSubscription.Builder()
            .user(user)
            .plan(plan)
            .expiresAt(expireDate)
            .status(SubscriptionStatus.ACTIVE)
            .build();

    UserSubscription savedUserSubscription = userSubscriptionRepository.save(newUserSubscription);
    return UserSubscriptionMapper.toResponse(savedUserSubscription);
  }

  @Override
  public void cancelUserSubscription(UUID userId) {
    userSubscriptionRepository.cancelUserSubscription(userId);
  }

  @Override
  public UserSubscriptionResponse getUserSubscriptionById(UUID userId) {
    UserSubscription userSubscriptionResponse = userSubscriptionRepository
            .getUserSubscriptionByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Nenhum plano ativo para este usuário"));
    return UserSubscriptionMapper.toResponse(userSubscriptionResponse);
  }

  @Override
  public Page<UserSubscriptionResponse> getAllUserSubscriptions(Pageable pageable) {
    return userSubscriptionRepository.findAll(pageable).map(UserSubscriptionMapper::toResponse);
  }

  private LocalDateTime calculateBillingCycle(SubscriptionPlan plan) {
    if("FREE".equalsIgnoreCase(plan.getBillingCycle().toString())) {
      return LocalDateTime.now().plusYears(20);
    }
    return switch (plan.getBillingCycle()){
      case MONTHLY -> LocalDateTime.now().plusMonths(1);
      case YEARLY ->  LocalDateTime.now().plusYears(1);
    };
  }
}
