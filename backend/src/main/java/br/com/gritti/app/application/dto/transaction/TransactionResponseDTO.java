package br.com.gritti.app.application.dto.transaction;

import br.com.gritti.app.application.dto.minimal.CategoryMinimalDTO;
import br.com.gritti.app.application.dto.minimal.InstallmentMinimalDTO;
import br.com.gritti.app.application.dto.minimal.InvoiceMinimalDTO;
import br.com.gritti.app.domain.enums.PaymentType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.UUID;

@Relation(collectionRelation = "transactions")
public class TransactionResponseDTO extends RepresentationModel<TransactionResponseDTO> {
  private UUID id;
  private Date timestamp;
  private Double value;
  private PaymentType paymentType;
  private CategoryMinimalDTO category;
  private InstallmentMinimalDTO installment;
  private InvoiceMinimalDTO invoice;

  public TransactionResponseDTO() {
  }

  public TransactionResponseDTO(UUID id, Date timestamp, Double value, PaymentType paymentType,
                                CategoryMinimalDTO category, InstallmentMinimalDTO installment, InvoiceMinimalDTO invoice) {
    this.id = id;
    this.timestamp = timestamp;
    this.value = value;
    this.paymentType = paymentType;
    this.category = category;
    this.installment = installment;
    this.invoice = invoice;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public CategoryMinimalDTO getCategory() {
    return category;
  }

  public void setCategory(CategoryMinimalDTO category) {
    this.category = category;
  }

  public InstallmentMinimalDTO getInstallment() {
    return installment;
  }

  public void setInstallment(InstallmentMinimalDTO installment) {
    this.installment = installment;
  }

  public InvoiceMinimalDTO getInvoice() {
    return invoice;
  }

  public void setInvoice(InvoiceMinimalDTO invoice) {
    this.invoice = invoice;
  }
}
