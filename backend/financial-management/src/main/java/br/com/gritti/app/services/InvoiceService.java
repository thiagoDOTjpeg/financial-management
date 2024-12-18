package br.com.gritti.app.services;

import br.com.gritti.app.dto.InvoiceDTO;
import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;
import br.com.gritti.app.repository.InvoiceRepository;
import br.com.gritti.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, TransactionRepository transactionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(invoices);
    }

    public ResponseEntity<InvoiceDTO> getInvoiceById(UUID id) throws Exception {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new Exception("Invoice not found"));
        List<Transaction> transaction = transactionRepository.findAllById_invoice(invoice.getId());
        double totalValue = transaction.stream().mapToDouble(Transaction::getValue).sum();
        InvoiceDTO response = new InvoiceDTO(invoice.getId(), invoice, totalValue, transaction);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Invoice> createInvoice(Invoice invoice){
        Invoice response = invoiceRepository.save(invoice);
        return ResponseEntity.status(201).body(    response);
    }


}
