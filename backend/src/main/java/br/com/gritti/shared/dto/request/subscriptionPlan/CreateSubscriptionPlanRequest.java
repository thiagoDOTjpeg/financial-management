package br.com.gritti.shared.dto.request.subscriptionPlan;

import br.com.gritti.domain.enums.BillingCycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateSubscriptionPlanRequest(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
        String name,

        @NotBlank(message = "O nome de exibição não pode estar em branco.")
        @Size(max = 100, message = "O nome de exibição deve ter no máximo 100 caracteres.")
        String displayName,

        @NotNull(message = "O preço não pode ser nulo.")
        @PositiveOrZero(message = "O preço deve ser zero ou um valor positivo.")
        BigDecimal price,

        @NotNull(message = "O ciclo de cobrança não pode ser nulo.")
        BillingCycle billingCycle,

        @NotNull(message = "A informação sobre anúncios (hasAds) não pode ser nula.")
        Boolean hasAds,

        @NotNull(message = "O número máximo de contas bancárias não pode ser nulo.")
        @PositiveOrZero(message = "O número máximo de contas deve ser zero ou um valor positivo.")
        Integer maxBankAccounts,

        @NotNull(message = "O número máximo de cartões não pode ser nulo.")
        @PositiveOrZero(message = "O número máximo de cartões deve ser zero ou um valor positivo.")
        Integer maxCards,

        @NotNull(message = "A informação sobre orçamentos (hasBudgets) não pode ser nula.")
        Boolean hasBudgets,

        @NotNull(message = "A informação sobre metas (hasGoals) não pode ser nula.")
        Boolean hasGoals,

        @NotNull(message = "A informação sobre relatórios (hasReports) não pode ser nula.")
        Boolean hasReports,

        @NotNull(message = "A informação sobre transações recorrentes não pode ser nula.")
        Boolean hasRecurringTransactions
) {
}
