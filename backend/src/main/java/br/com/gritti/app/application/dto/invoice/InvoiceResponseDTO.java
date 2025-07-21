package br.com.gritti.app.application.dto.invoice;

import br.com.gritti.app.application.dto.minimal.CardMinimalDTO;
import br.com.gritti.app.application.dto.minimal.InstallmentMinimalDTO;
import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.Installment;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InvoiceResponseDTO extends RepresentationModel<InvoiceResponseDTO> {
  private UUID id;
  private Date billingMonth;
  private Double totalValue;
  private InvoiceStatus status;
  private Date closingDate;
  private CardMinimalDTO card;
  private Set<InstallmentMinimalDTO> installments = new HashSet<>();

  public InvoiceResponseDTO() {
  }

  public InvoiceResponseDTO(UUID id, Date billingMonth, Double totalValue, InvoiceStatus status,
                            Date closingDate, CardMinimalDTO card, Set<InstallmentMinimalDTO> installments) {
    this.id = id;
    this.billingMonth = billingMonth;
    this.totalValue = totalValue;
    this.status = status;
    this.closingDate = closingDate;
    this.card = card;
    this.installments = installments;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getBillingMonth() {
    return billingMonth;
  }

  public void setBillingMonth(Date billingMonth) {
    this.billingMonth = billingMonth;
  }

  public Double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(Double totalValue) {
    this.totalValue = totalValue;
  }

  public InvoiceStatus getStatus() {
    return status;
  }

  public void setStatus(InvoiceStatus status) {
    this.status = status;
  }

  public Date getClosingDate() {
    return closingDate;
  }

  public void setClosingDate(Date closingDate) {
    this.closingDate = closingDate;
  }

  public CardMinimalDTO getCard() {
    return card;
  }

  public void setCard(CardMinimalDTO card) {
    this.card = card;
  }

  public Set<InstallmentMinimalDTO> getInstallments() {
    return installments;
  }

  public void setInstallments(Set<InstallmentMinimalDTO> installments) {
    this.installments = installments;
  }
}
