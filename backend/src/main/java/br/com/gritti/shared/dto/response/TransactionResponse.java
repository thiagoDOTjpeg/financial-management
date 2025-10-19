package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import br.com.gritti.shared.dto.response.summary.CategorySummaryResponse;
import br.com.gritti.shared.dto.response.summary.InstallmentSummaryResponse;
import br.com.gritti.shared.dto.response.summary.RecurringTransactionSummaryResponse;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Relation(collectionRelation = "transaction")
public class TransactionResponse {
  private UUID id;
  private CategorySummaryResponse category;
  private String description;
  private BigDecimal amount;
  private LocalDate transactionDate;
  private TransactionType transactionType;
  private PaymentType paymentType;
  private BankAccountResponse bankAccount;
  private InvoiceResponse invoice;
  private InstallmentSummaryResponse installment;
  private RecurringTransactionSummaryResponse recurringTransaction;
  private Integer installmentNumber;
  private String notes;

  public TransactionResponse() {
  }

  public TransactionResponse(UUID id, CategorySummaryResponse category, String description, BigDecimal amount, LocalDate transactionDate,
                             TransactionType transactionType, PaymentType paymentType, BankAccountResponse bankAccount, InvoiceResponse invoice,
                             InstallmentSummaryResponse installment, RecurringTransactionSummaryResponse recurringTransaction, Integer installmentNumber, String notes) {
    this.id = id;
    this.category = category;
    this.description = description;
    this.amount = amount;
    this.transactionDate = transactionDate;
    this.transactionType = transactionType;
    this.paymentType = paymentType;
    this.bankAccount = bankAccount;
    this.invoice = invoice;
    this.installment = installment;
    this.recurringTransaction = recurringTransaction;
    this.installmentNumber = installmentNumber;
    this.notes = notes;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CategorySummaryResponse getCategory() {
    return category;
  }

  public void setCategory(CategorySummaryResponse category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public BankAccountResponse getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(BankAccountResponse bankAccount) {
    this.bankAccount = bankAccount;
  }

  public InvoiceResponse getInvoice() {
    return invoice;
  }

  public void setInvoice(InvoiceResponse invoice) {
    this.invoice = invoice;
  }

  public InstallmentSummaryResponse getInstallment() {
    return installment;
  }

  public void setInstallment(InstallmentSummaryResponse installment) {
    this.installment = installment;
  }

  public RecurringTransactionSummaryResponse getRecurringTransaction() {
    return recurringTransaction;
  }

  public void setRecurringTransaction(RecurringTransactionSummaryResponse recurringTransaction) {
    this.recurringTransaction = recurringTransaction;
  }

  public Integer getInstallmentNumber() {
    return installmentNumber;
  }

  public void setInstallmentNumber(Integer installmentNumber) {
    this.installmentNumber = installmentNumber;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
