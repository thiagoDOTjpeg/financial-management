package br.com.gritti.app.domain.model;

import br.com.gritti.app.infra.entity.Auditable;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "installments")
public class Installment extends Auditable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number_installment", nullable = false)
    private Integer numberInstallment;

    @Column(name = "installment_value", nullable = false)
    private Double installmentValue;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "id_transaction")
    private String idTransaction;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Invoice> invoices;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumberInstallment() {
        return numberInstallment;
    }

    public void setNumberInstallment(Integer numberInstallment) {
        this.numberInstallment = numberInstallment;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Installment that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
