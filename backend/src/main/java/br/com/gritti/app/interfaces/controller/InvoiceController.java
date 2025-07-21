package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.invoice.InvoiceResponseDTO;
import br.com.gritti.app.application.service.InvoiceApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
