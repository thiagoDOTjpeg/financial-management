package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.UserSubscriptionService;
import br.com.gritti.domain.model.UserSubscription;
import br.com.gritti.domain.repository.UserSubscriptionRepository;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.UserSubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

  private final UserSubscriptionRepository userSubscriptionRepository;
  private final PagedResourcesAssembler<UserSubscriptionResponse> pagedResourcesAssembler;

  @Autowired
  public UserSubscriptionServiceImpl(UserSubscriptionRepository userSubscriptionRepository, PagedResourcesAssembler<UserSubscriptionResponse>  pagedResourcesAssembler) {
    this.userSubscriptionRepository = userSubscriptionRepository;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @Override
  public UserSubscriptionResponse createUserSubscription(CreateUserSubscriptionRequest request) {
    //TODO
    return null;
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
  public PagedModel<EntityModel<UserSubscriptionResponse>> getAllUserSubscriptions(PageRequest pageable) {
    Page<UserSubscriptionResponse> userSubscriptionResponsePage = userSubscriptionRepository.findAll(pageable).map(UserSubscriptionMapper::toResponse);
    return pagedResourcesAssembler.toModel(userSubscriptionResponsePage);
  }
}
