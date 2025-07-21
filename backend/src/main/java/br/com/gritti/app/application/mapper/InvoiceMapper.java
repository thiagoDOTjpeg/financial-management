package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.invoice.InvoiceCreateDTO;
import br.com.gritti.app.application.dto.invoice.InvoiceResponseDTO;
import br.com.gritti.app.domain.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
  Invoice invoiceCreateDTOtoInvoice(InvoiceCreateDTO invoiceCreateDTO);

  InvoiceResponseDTO invoiceToInvoiceResponseDTO(Invoice invoice);
}
