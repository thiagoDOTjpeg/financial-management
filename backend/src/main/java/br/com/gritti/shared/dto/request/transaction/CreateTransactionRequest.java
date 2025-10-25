package br.com.gritti.shared.dto.request.transaction;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.RecurringFrequency;
import br.com.gritti.domain.enums.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull(message = "Categoria é obrigatória")
        UUID categoryId,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 255)
        String description,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal amount,

        @NotNull(message = "Data é obrigatória")
        LocalDate transactionDate,

        @NotNull(message = "Tipo de transação é obrigatório")
        TransactionType transactionType,

        @NotNull(message = "Tipo de pagamento é obrigatório")
        PaymentType paymentType,

        UUID bankAccountId,
        UUID cardId,
        String notes,
        @Min(value = 2, message = "Parcelamento mínimo: 2x")
        @Max(value = 48, message = "Parcelamento máximo: 48x")
        Integer installments,
        Boolean isRecurring,
        RecurringFrequency frequency,
        Integer dayOfMonth,
        LocalDate recurringEndDate,
        UUID goalId
) { }
