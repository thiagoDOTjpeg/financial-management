package br.com.gritti.shared.dto.request.transaction;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import br.com.gritti.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateTransactionRequest(
        @NotNull(message = "O ID da categoria não pode ser nulo.")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "O ID da categoria não está em um formato UUID válido.")
        String categoryId,

        @NotBlank(message = "A descrição não pode estar em branco.")
        @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres.")
        String description,

        @NotNull(message = "O valor (amount) não pode ser nulo.")
        @Positive(message = "O valor da transação deve ser positivo.")
        BigDecimal amount,

        @NotNull(message = "A data da transação não pode ser nula.")
        @PastOrPresent(message = "A data da transação não pode ser no futuro.")
        LocalDateTime transactionDate,

        @NotNull(message = "O tipo da transação (transactionType) não pode ser nulo.")
        TransactionType transactionType,

        @NotNull(message = "O tipo de pagamento (paymentType) não pode ser nulo.")
        PaymentType paymentType,

        @NotNull(message = "O ID da parcela não pode ser nulo.")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "O ID da parcela não está em um formato UUID válido.")
        String installmentId,

        @Min(value = 1, message = "O número da parcela deve ser no mínimo 1.")
        Integer installmentNumber,

        @Size(max = 255, message = "As anotações devem ter no máximo 255 caracteres.")
        String notes
) {
}
