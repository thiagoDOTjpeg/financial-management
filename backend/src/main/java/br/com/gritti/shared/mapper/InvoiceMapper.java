package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Invoice;
import br.com.gritti.shared.dto.response.InvoiceResponse;

public class InvoiceMapper {
  public static InvoiceResponse toResponse(Invoice invoice) {
    InvoiceResponse response = new InvoiceResponse();
    response.setId(invoice.getId());
    response.setBillingMonth(invoice.getBillingMonth());
    response.setClosingDate(invoice.getClosingDate());
    response.setStatus(invoice.getStatus());
    response.setPaidAt(invoice.getPaidAt());
    response.setDueDate(invoice.getDueDate());
    response.setCard(CardMapper.toSummaryResponse(invoice.getCard()));
    return response;
  }
}
