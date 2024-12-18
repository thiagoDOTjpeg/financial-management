package br.com.gritti.app.controllers;

import br.com.gritti.app.dto.InvoiceDTO;
import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {


    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable UUID id) throws Exception {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice)  {
        return invoiceService.createInvoice(invoice);
    }
}
