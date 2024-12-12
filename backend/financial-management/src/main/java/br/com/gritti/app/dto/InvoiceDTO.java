package br.com.gritti.app.dto;

import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;

import java.util.List;
import java.util.UUID;

public class InvoiceDTO {

    private UUID id;
    private Invoice invoiceContent;
    private Double valorTotal;
    private List<Transaction> transactionContent;

    public InvoiceDTO(Invoice invoiceContent, List<Transaction> transactionContent) {
        this.invoiceContent = invoiceContent;
        this.transactionContent = transactionContent;
    }

    public Invoice getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(Invoice invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<Transaction> getTransactionContent() {
        return transactionContent;
    }

    public void setTransactionContent(List<Transaction> transactionContent) {
        this.transactionContent = transactionContent;
    }
}
