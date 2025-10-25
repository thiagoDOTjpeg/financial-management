package br.com.gritti.shared.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CancelInstallmentResponse(
        UUID installmentId,
        String description,
        Integer cancelledTransactions,
        BigDecimal totalAmount,
        String message
) {
}
