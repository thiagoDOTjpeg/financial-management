package br.com.gritti.app.controllers;

import br.com.gritti.app.dto.InvoiceDTO;
import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.repository.InvoiceRepository;
import br.com.gritti.app.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /*
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceDTO getInvoiceById(@PathVariable UUID id) throws Exception {
      return invoiceService.getInvoiceById(id);
    }*/

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice createInvoice(@RequestBody Invoice invoice)  {
      return invoiceService.createInvoice(invoice);
    }
}
