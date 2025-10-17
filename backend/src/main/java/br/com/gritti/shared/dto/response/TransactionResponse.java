package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.TransactionType;
import br.com.gritti.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
  private Category category;
  private String description;
  private BigDecimal amount;
  private LocalDateTime transactionDate;
  private TransactionType transactionType;
  private PaymentType paymentType;
  private BankAccountResponse bankAccount;
  private InvoiceResponse invoice;
  private Installment installment;
  private RecurringTransaction recurringTransaction;
  private Integer installmentNumber;
  private String notes;

  public TransactionResponse() {
  }

  public TransactionResponse(Category category, String description, BigDecimal amount,
                            LocalDateTime transactionDate, TransactionType transactionType, PaymentType paymentType,
                             BankAccountResponse bankAccount, InvoiceResponse invoice, Installment installment, RecurringTransaction recurringTransaction,
                            Integer installmentNumber, String notes) {
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
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
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

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
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

  public Installment getInstallment() {
    return installment;
  }

  public void setInstallment(Installment installment) {
    this.installment = installment;
  }

  public RecurringTransaction getRecurringTransaction() {
    return recurringTransaction;
  }

  public void setRecurringTransaction(RecurringTransaction recurringTransaction) {
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
