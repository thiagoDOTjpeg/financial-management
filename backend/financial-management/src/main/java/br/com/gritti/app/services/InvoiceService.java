package br.com.gritti.app.services;

import br.com.gritti.app.dto.InvoiceDTO;
import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;
import br.com.gritti.app.repository.InvoiceRepository;
import br.com.gritti.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, TransactionRepository transactionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
    }
    public InvoiceDTO getInvoiceById(UUID id) throws Exception {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new Exception("Invoice not found"));
        List<Transaction> transaction = transactionRepository.findAllById_invoice(invoice.getId());
        InvoiceDTO response = new InvoiceDTO(invoice, transaction);
        return response;
    }

    public Invoice createInvoice(Invoice invoice){
      return invoiceRepository.save(invoice);
    }


}
