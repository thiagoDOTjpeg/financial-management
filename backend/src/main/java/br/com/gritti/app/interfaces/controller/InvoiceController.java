package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.invoice.InvoiceResponseDTO;
import br.com.gritti.app.application.service.InvoiceApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Invoice", description = "Operações relacionado as faturas")
public class InvoiceController {
  private final Logger log = LoggerFactory.getLogger(InvoiceController.class);
  private final InvoiceApplicationService invoiceApplicationService;

  @Autowired
  public InvoiceController(InvoiceApplicationService invoiceApplicationService) {
    this.invoiceApplicationService = invoiceApplicationService;
  }

  @GetMapping(value = "/invoices/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable("invoiceId")UUID invoiceId){
    log.info("CONTROLLER: Received request to get invoice with id {} and passing to the application", invoiceId);
    return ResponseEntity.ok(invoiceApplicationService.getInvoiceById(invoiceId));
  }

  @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<InvoiceResponseDTO>>> getInvoices(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ) {
    log.info("CONTROLLER: Received request to get all invoices and passing to the application");
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "billingMonth"));
    return ResponseEntity.ok(invoiceApplicationService.getInvoices(pageable, username));
  }
}
