package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.invoice.InvoiceCreateDTO;
import br.com.gritti.app.domain.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
  Invoice invoiceCreateDTOtoInvoice(InvoiceCreateDTO invoiceCreateDTO);
}
