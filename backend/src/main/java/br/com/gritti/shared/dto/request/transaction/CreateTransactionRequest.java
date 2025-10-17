package br.com.gritti.shared.dto.request.transaction;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import br.com.gritti.domain.model.BankAccount;
import br.com.gritti.domain.model.Category;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionRequest(
        @NotNull(message = "A categoria não pode ser nulo.")
        Category category,

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

        @NotNull(message = "A conta bancária não pode ser nulo.")
        BankAccount bankAccount,

        @Min(value = 1, message = "O número da parcela deve ser no mínimo 1.")
        @Nullable()
        Integer installmentNumber,

        @Size(max = 500, message = "As anotações devem ter no máximo 500 caracteres.")
        String notes
) {
}
