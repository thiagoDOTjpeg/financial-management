package br.com.gritti.app.domain.service.strategy;

import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.service.strategy.impl.CreditTransactionStrategy;
import br.com.gritti.app.domain.service.strategy.impl.DebitTransactionStrategy;
import br.com.gritti.app.domain.service.strategy.impl.InstallmentTransactionStrategy;
import br.com.gritti.app.domain.service.strategy.impl.TransferTransactionStrategy;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.shared.exceptions.InvalidPaymentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionProcessingStrategyFactory {

  private final Map<PaymentType, TransactionProcessingStrategy> strategies;
  private final InstallmentTransactionStrategy installmentStrategy;

  @Autowired
  public TransactionProcessingStrategyFactory(CreditTransactionStrategy creditStrategy,
                                              InstallmentTransactionStrategy installmentStrategy,
                                              DebitTransactionStrategy debitStrategy,
                                              TransferTransactionStrategy transferStrategy) {
    this.installmentStrategy = installmentStrategy;
    this.strategies = Map.of(
            PaymentType.CREDIT, creditStrategy,
            PaymentType.DEBIT, debitStrategy,
            PaymentType.TRANSFER, transferStrategy
    );
  }

  public TransactionProcessingStrategy getStrategy(PaymentType paymentType,
                                                   TransactionProcessingData processingData) {

    if (paymentType == PaymentType.CREDIT && hasInstallmentData(processingData)) {
      return installmentStrategy;
    }

    TransactionProcessingStrategy strategy = strategies.get(paymentType);
    if (strategy == null) {
      throw new InvalidPaymentTypeException("Invalid payment type: " + paymentType);
    }
    return strategy;
  }

  private boolean hasInstallmentData(TransactionProcessingData processingData) {
    return processingData.getInstallmentData() != null &&
            (processingData.getInstallmentData().getInstallmentValue() != null ||
                    processingData.getInstallmentData().getNumberInstallment() != null);
  }
}
