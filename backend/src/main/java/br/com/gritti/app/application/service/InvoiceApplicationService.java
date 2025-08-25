package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.invoice.InvoiceResponseDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.InvoiceMapper;
import br.com.gritti.app.domain.model.Invoice;
import br.com.gritti.app.domain.service.InvoiceDomainService;
import br.com.gritti.app.shared.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceApplicationService {
  private final Logger log = LoggerFactory.getLogger(InvoiceApplicationService.class);
  private final InvoiceDomainService invoiceDomainService;
  private final PagedResourcesAssembler<InvoiceResponseDTO> assembler;
  private final InvoiceMapper invoiceMapper;

  @Autowired
  public InvoiceApplicationService(InvoiceDomainService invoiceDomainService, InvoiceMapper invoiceMapper, PagedResourcesAssembler<InvoiceResponseDTO> assembler) {
    this.assembler = assembler;
    this.invoiceDomainService = invoiceDomainService;
    this.invoiceMapper = invoiceMapper;
  }

  public InvoiceResponseDTO getInvoiceById(UUID id) {
    log.info("APPLICATION: Received the request from the Controller and passing to the Domain Service");
    Invoice invoice = invoiceDomainService.getInvoiceById(id);
    System.out.println(invoice.getInstallments());
    return invoiceMapper.invoiceToInvoiceResponseDTO(invoice);
  }

  public PagedModel<EntityModel<InvoiceResponseDTO>> getInvoices(Pageable pageable, String username) {
    log.info("APPLICATION: Request received from controller and passing to the Domain Service");
    String currentUsername = SecurityUtil.getCurrentUsername();
    boolean isAdmin = SecurityUtil.isAdmin();
    String usernameToUse = isAdmin ? username : currentUsername;
    Page<Invoice> invoices;
    if(username != null && !username.isBlank()) {
      if(!isAdmin && !username.equals(currentUsername)){
        throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
      }
      invoices = invoiceDomainService.getInvoices(pageable, usernameToUse);
    } else if(!isAdmin){
      username = currentUsername;
      invoices = invoiceDomainService.getInvoices(pageable, username);
    } else {
      invoices = invoiceDomainService.getInvoices(pageable);
    }

    Page<InvoiceResponseDTO> transactionsWithLinks = invoices.map(invoiceMapper::invoiceToInvoiceResponseDTO);
    return assembler.toModel(transactionsWithLinks);
  }

}
