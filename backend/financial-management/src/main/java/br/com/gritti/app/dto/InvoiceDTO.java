package br.com.gritti.app.dto;

import br.com.gritti.app.models.Invoice;
import br.com.gritti.app.models.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InvoiceDTO {

    private UUID id;
    private Invoice invoiceContent;
    private Double valorTotal;
    private List<Transaction> transactionsContent;

    public InvoiceDTO(UUID id, Invoice invoiceContent, Double valorTotal, List<Transaction> transactionContent ) {
        this.id = id;
        this.invoiceContent = invoiceContent;
        this.valorTotal = valorTotal;
        this.transactionsContent = transactionContent;
    }

    public Invoice getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(Invoice invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<Transaction> getTransactionContent() {
        return transactionsContent;
    }

    public void setTransactionsContent(List<Transaction> transactionContent) {
        this.transactionsContent = transactionContent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDTO that = (InvoiceDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(invoiceContent, that.invoiceContent) && Objects.equals(valorTotal, that.valorTotal) && Objects.equals(transactionsContent, that.transactionsContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceContent, valorTotal, transactionsContent);
    }
}
