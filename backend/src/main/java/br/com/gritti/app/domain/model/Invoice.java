package br.com.gritti.app.domain.model;

import br.com.gritti.app.domain.enums.InvoiceStatus;
import br.com.gritti.app.infra.entity.Auditable;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "invoices")
public class Invoice extends Auditable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Temporal(TemporalType.DATE)
    private Date billingMonth;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @Column
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "closing_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date closingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_card")
    private Card card;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "invoices_installments",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "installment_id"))
    private Set<Installment> installments = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(Date billingMonth) {
        this.billingMonth = billingMonth;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Set<Installment> getInstallment() {
        return installments;
    }

    public void setInstallment(Set<Installment> installment) {
        this.installments = installment;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Invoice invoice)) return false;
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
