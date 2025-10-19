package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.TransactionService;
import br.com.gritti.domain.enums.GoalStatus;
import br.com.gritti.domain.enums.PaymentType;
import br.com.gritti.domain.enums.RecurringFrequency;
import br.com.gritti.domain.enums.TransactionType;
import br.com.gritti.domain.model.*;
import br.com.gritti.domain.repository.*;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.request.transaction.UpdateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;
import br.com.gritti.shared.exception.BusinessException;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;
  private final InstallmentRepository installmentRepository;
  private final FinanceGoalRepository financialGoalRepository;
  private final InvoiceRepository invoiceRepository;
  private final RecurringTransactionRepository recurringRepository;
  private final CategoryRepository categoryRepository;
  private final CardRepository cardRepository;
  private final BankAccountRepository bankAccountRepository;
  private final GoalTransactionRepository goalTransactionRepository;

  public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, InstallmentRepository installmentRepository,
                                FinanceGoalRepository financialGoalRepository, InvoiceRepository invoiceRepository,
                                RecurringTransactionRepository recurringRepository, CategoryRepository categoryRepository, CardRepository cardRepository,
                                BankAccountRepository bankAccountRepository, GoalTransactionRepository goalTransactionRepository) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
    this.installmentRepository = installmentRepository;
    this.financialGoalRepository = financialGoalRepository;
    this.invoiceRepository = invoiceRepository;
    this.recurringRepository = recurringRepository;
    this.categoryRepository = categoryRepository;
    this.cardRepository = cardRepository;
    this.bankAccountRepository = bankAccountRepository;
    this.goalTransactionRepository = goalTransactionRepository;
  }

  @Override
  public TransactionResponse getById(UUID id) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
    return TransactionMapper.toResponse(transaction);
  }

  @Override
  public Page<TransactionResponse> getAll(Pageable pageable) {
    Page<Transaction> transactionPage = transactionRepository.findAllByDeletedAtIsNull(pageable);
    return transactionPage.map(TransactionMapper::toResponse);
  }

  @Override
  public TransactionResponse updateTransaction(UUID id, UpdateTransactionRequest request) {
    Transaction response = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
    //TODO
    return null;
  }

  @Override
  public void softDelete(UUID id) {
    transactionRepository.softDelete(id);
  }

  @Override
  @Transactional
  public TransactionResponse createTransaction(UUID userId, CreateTransactionRequest request) {
    validateTransaction(userId, request);
    TransactionResponse response;

    if (isInstallment(request)) {
      response = createInstallmentTransaction(userId, request);
    } else if (isRecurring(request)) {
      response = createRecurringTransaction(userId, request);
    } else {
      response = createSingleTransaction(userId, request);
    }

    if (request.goalId() != null) {
      linkToGoal(userId, response.getId(), request.goalId(), request.transactionType());
    }

    return response;
  }

  private boolean isInstallment(CreateTransactionRequest request) {
    return request.installments() != null && request.installments() > 1;
  }

  private boolean isRecurring(CreateTransactionRequest request) {
    return Boolean.TRUE.equals(request.isRecurring());
  }

  private void validateTransaction(UUID userId, CreateTransactionRequest request) {
    categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.categoryId()));

    if (request.paymentType() == PaymentType.CREDIT) {
      if (request.cardId() == null) {
        throw new BusinessException("Cartão é obrigatório para pagamento em crédito");
      }
      cardRepository.findById(request.cardId())
              .orElseThrow(() -> new ResourceNotFoundException("Cartão", request.cardId()));
    }

    if (request.paymentType() == PaymentType.DEBIT ||
            request.paymentType() == PaymentType.TRANSFER) {
      if (request.bankAccountId() == null) {
        throw new BusinessException("Conta bancária é obrigatória para débito/transferência");
      }
      bankAccountRepository.findById(request.bankAccountId())
              .orElseThrow(() -> new ResourceNotFoundException("Conta", request.bankAccountId()));
    }

    if (isRecurring(request)) {
      if (request.frequency() == null) {
        throw new BusinessException("Frequência é obrigatória para transação recorrente");
      }
      if (request.frequency() == RecurringFrequency.MONTHLY && request.dayOfMonth() == null) {
        throw new BusinessException("Dia do mês é obrigatório para recorrência mensal");
      }
    }

    if (request.goalId() != null) {
      if (request.transactionType() != TransactionType.INCOME) {
        throw new BusinessException(
                "Apenas receitas (INCOME) podem ser vinculadas a metas financeiras",
                "INVALID_GOAL_TRANSACTION_TYPE"
        );
      }

      FinancialGoal goal = financialGoalRepository.findById(request.goalId())
              .orElseThrow(() -> new ResourceNotFoundException("Meta financeira", request.goalId()));

      if (!goal.getUser().getId().equals(userId)) {
        throw new BusinessException(
                "Você não tem permissão para vincular transações a esta meta",
                "GOAL_ACCESS_DENIED"
        );
      }

      if (goal.getStatus() != GoalStatus.IN_PROGRESS) {
        throw new BusinessException(
                "Não é possível vincular transações a uma meta " + goal.getStatus(),
                "INVALID_GOAL_STATUS"
        );
      }
    }

    if (isInstallment(request) && request.paymentType() != PaymentType.CREDIT) {
      throw new BusinessException(
              "Parcelamento só é permitido para pagamentos em crédito",
              "INSTALLMENT_ONLY_CREDIT"
      );
    }

    if (isInstallment(request) && request.goalId() != null) {
      throw new BusinessException(
              "Transações parceladas não podem ser vinculadas a metas",
              "INSTALLMENT_NO_GOAL"
      );
    }

    if (isRecurring(request) && request.goalId() != null) {
      throw new BusinessException(
              "Transações recorrentes não podem ser vinculadas a metas diretamente. " +
                      "Vincule cada ocorrência individualmente.",
              "RECURRING_NO_GOAL"
      );
    }
  }

  private TransactionResponse createInstallmentTransaction(UUID userId, CreateTransactionRequest request) {
    Card card = cardRepository.findById(request.cardId())
            .orElseThrow(() -> new ResourceNotFoundException("Cartão", request.cardId()));

    BigDecimal installmentValue = request.amount().divide(
            new BigDecimal(request.installments()),
            2,
            RoundingMode.HALF_UP
    );

    Installment installment = new Installment.Builder()
            .card(card)
            .description(request.description())
            .totalAmount(request.amount())
            .totalInstallments(request.installments())
            .installmentValue(installmentValue)
            .firstDueDate(request.transactionDate())
            .build();

    Installment saved = installmentRepository.save(installment);

    Transaction firstTransaction = transactionRepository
            .findFirstByInstallmentIdOrderByInstallmentNumber(saved.getId())
            .orElseThrow(() -> new BusinessException("Erro ao gerar parcelas"));

    return TransactionMapper.toResponse(firstTransaction);
  }

  private TransactionResponse createRecurringTransaction(UUID userId, CreateTransactionRequest request) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", userId));

    RecurringTransaction recurring = new RecurringTransaction.Builder()
            .user(user)
            .category(categoryRepository.getReferenceById(request.categoryId()))
            .description(request.description())
            .amount(request.amount())
            .frequency(request.frequency())
            .dayOfMonth(request.dayOfMonth())
            .paymentType(request.paymentType())
            .bankAccount(request.bankAccountId() != null ?
                    bankAccountRepository.getReferenceById(request.bankAccountId()) : null)
            .card(request.cardId() != null ?
                    cardRepository.getReferenceById(request.cardId()) : null)
            .startDate(request.transactionDate())
            .endDate(request.recurringEndDate())
            .isActive(true)
            .build();

    RecurringTransaction saved = recurringRepository.save(recurring);

    Transaction firstTransaction = findFirstRecurringTransaction(saved);

    return TransactionMapper.toResponse(firstTransaction);
  }

  private Transaction findFirstRecurringTransaction(RecurringTransaction recurring) {
    return transactionRepository.findFirstTransactionByRecurringTransactionOrderByCreatedAtAsc(recurring)
            .orElseThrow(() -> new BusinessException("Erro ao gerar parcelas"));
  }

  private TransactionResponse createSingleTransaction(UUID userId, CreateTransactionRequest request) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", userId));

    Invoice invoice = null;
    if (request.paymentType() == PaymentType.CREDIT) {
      invoice = findOrCreateInvoice(request.cardId(), request.transactionDate());
    }

    Transaction transaction = new Transaction.Builder()
            .user(user)
            .category(categoryRepository.getReferenceById(request.categoryId()))
            .description(request.description())
            .amount(request.amount())
            .transactionDate(request.transactionDate())
            .transactionType(request.transactionType())
            .paymentType(request.paymentType())
            .bankAccount(request.bankAccountId() != null ?
                    bankAccountRepository.getReferenceById(request.bankAccountId()) : null)
            .invoice(invoice)
            .notes(request.notes())
            .build();

    Transaction saved = transactionRepository.save(transaction);

    return TransactionMapper.toResponse(saved);
  }

  private Invoice findOrCreateInvoice(UUID cardId, LocalDate transactionDate) {
    LocalDate billingMonth = LocalDate.of(
            transactionDate.getYear(),
            transactionDate.getMonth(),
            1
    );

    return invoiceRepository
            .findByCardIdAndBillingMonth(cardId, billingMonth)
            .orElseThrow(() -> new BusinessException(
                    "Fatura não encontrada. As faturas são geradas automaticamente ao criar o cartão."
            ));
  }

  private void linkToGoal(UUID userId, UUID transactionId, UUID goalId, TransactionType transactionType) {
    if (transactionType != TransactionType.INCOME) {
      throw new BusinessException(
              "Apenas receitas podem ser vinculadas a metas",
              "INVALID_GOAL_TRANSACTION"
      );
    }
    FinancialGoal goal = financialGoalRepository.findById(goalId)
            .orElseThrow(() -> new ResourceNotFoundException("Meta", goalId));
    if (!goal.getUser().getId().equals(userId)) {
      throw new BusinessException(
              "Você não tem permissão para vincular transações a esta meta",
              "GOAL_ACCESS_DENIED"
      );
    }

    if (goal.getStatus() != GoalStatus.IN_PROGRESS) {
      throw new BusinessException(
              "Não é possível vincular transações a uma meta " + goal.getStatus(),
              "INVALID_GOAL_STATUS"
      );
    }

    Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new ResourceNotFoundException("Transação", transactionId));

    GoalTransaction goalTransaction = new GoalTransaction(goal, transaction);

    goalTransactionRepository.save(goalTransaction);
  }

}
