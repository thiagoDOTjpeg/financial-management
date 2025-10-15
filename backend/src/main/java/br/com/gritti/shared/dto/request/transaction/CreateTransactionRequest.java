package br.com.gritti.shared.dto.request.transaction;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionRequest(
        @NotNull(message = "O ID da categoria não pode ser nulo.")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "O ID da categoria não está em um formato UUID válido.")
        String categoryId,

        @NotBlank(message = "A descrição não pode estar em branco.")
        @Size(min = 3, max = 150, message = "A descrição deve ter entre 3 e 150 caracteres.")
        String description,

        @NotNull(message = "O valor não pode ser nulo.")
        @Positive(message = "O valor deve ser um número positivo.")
        BigDecimal amount,

        @NotNull(message = "A data da transação não pode ser nula.")
        @PastOrPresent(message = "A data da transação não pode ser no futuro.")
        LocalDateTime transactionDate,

        @NotNull(message = "O tipo da transação não pode ser nulo.")
        TransactionType transactionType,

        @NotNull(message = "O tipo de pagamento não pode ser nulo.")
        PaymentType paymentType,

        @Min(value = 1, message = "O número da parcela deve ser no mínimo 1.")
        @Nullable()
        Integer installmentNumber,

        @Size(max = 500, message = "As anotações devem ter no máximo 500 caracteres.")
        String notes
) {
}
