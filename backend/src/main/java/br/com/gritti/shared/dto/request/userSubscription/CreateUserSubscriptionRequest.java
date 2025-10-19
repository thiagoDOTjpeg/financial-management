package br.com.gritti.shared.dto.request.userSubscription;

import br.com.gritti.domain.enums.BillingCycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateUserSubscriptionRequest(
        UUID planId,
        BillingCycle billingCycle
) { }
