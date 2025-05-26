package br.com.gritti.app.application.dto.transaction;

import br.com.gritti.app.application.valueobjects.CategoryMinimalVO;
import br.com.gritti.app.domain.enums.PaymentType;

import java.util.Date;
import java.util.UUID;

public class TransactionResponseDTO {
  private UUID id;
  private Date timestamp;
  private Double value;
  private PaymentType paymentType;
  private CategoryMinimalVO category;

}
