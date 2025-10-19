-- =====================================================
-- TRIGGERS DO SISTEMA FINANCEIRO
-- =====================================================

-- =====================================================
-- 1. TRIGGER: GERAR FATURAS AUTOMATICAMENTE
-- =====================================================
-- Quando um cartão é criado, gera as faturas dos próximos 12 meses

CREATE OR REPLACE FUNCTION generate_invoices_for_new_card()
RETURNS TRIGGER AS $$
DECLARE
v_billing_month DATE;
    v_closing_date DATE;
    v_due_date DATE;
    v_month_offset INTEGER;
BEGIN
    -- Gerar faturas para os próximos 12 meses
FOR v_month_offset IN 0..11 LOOP
        -- Calcular mês de referência
        v_billing_month := DATE_TRUNC('month', CURRENT_DATE) + (v_month_offset || ' months')::INTERVAL;

        -- Calcular data de fechamento (dia de fechamento do mês de referência)
        v_closing_date := DATE_TRUNC('month', v_billing_month) +
                         (NEW.closing_day - 1 || ' days')::INTERVAL;

        -- Calcular data de vencimento (dia de vencimento do mês seguinte)
        v_due_date := DATE_TRUNC('month', v_billing_month) +
                     '1 month'::INTERVAL +
                     (NEW.due_day - 1 || ' days')::INTERVAL;

        -- Inserir fatura
INSERT INTO invoices (
    id,
    card_id,
    billing_month,
    closing_date,
    due_date,
    status,
    created_at,
    created_by
) VALUES (
             gen_random_uuid(),
             NEW.id,
             v_billing_month,
             v_closing_date,
             v_due_date,
             'OPEN',
             CURRENT_TIMESTAMP,
             NEW.created_by
         );
END LOOP;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_invoices_after_card_insert
    AFTER INSERT ON cards
    FOR EACH ROW
    WHEN (NEW.deleted_at IS NULL)
    EXECUTE FUNCTION generate_invoices_for_new_card();

COMMENT ON FUNCTION generate_invoices_for_new_card() IS
'Gera automaticamente faturas para os próximos 12 meses quando um novo cartão é criado';

-- =====================================================
-- 2. TRIGGER: FECHAR FATURA AUTOMATICAMENTE
-- =====================================================
-- Quando a data de fechamento passa, fecha a fatura automaticamente

CREATE OR REPLACE FUNCTION auto_close_invoices()
RETURNS VOID AS $$
BEGIN
UPDATE invoices
SET
    status = 'CLOSED',
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE status = 'OPEN'
  AND closing_date < CURRENT_DATE
  AND deleted_at IS NULL;
END;
$$ LANGUAGE plpgsql;

-- Executar via cron/scheduler (exemplo para executar diariamente):
-- SELECT cron.schedule('auto-close-invoices', '0 0 * * *', 'SELECT auto_close_invoices()');

COMMENT ON FUNCTION auto_close_invoices() IS
'Fecha automaticamente faturas que passaram da data de fechamento. Executar via scheduler diário.';

-- =====================================================
-- 3. TRIGGER: MARCAR FATURA COMO VENCIDA
-- =====================================================
-- Quando a data de vencimento passa e não foi paga, marca como OVERDUE

CREATE OR REPLACE FUNCTION mark_invoices_overdue()
RETURNS VOID AS $$
BEGIN
UPDATE invoices
SET
    status = 'OVERDUE',
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE status IN ('OPEN', 'CLOSED')
  AND due_date < CURRENT_DATE
  AND paid_at IS NULL
  AND deleted_at IS NULL;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION mark_invoices_overdue() IS
'Marca faturas como vencidas quando passam da data de vencimento sem pagamento. Executar via scheduler diário.';

-- =====================================================
-- 4. TRIGGER: CRIAR TRANSAÇÕES PARCELADAS
-- =====================================================
-- Quando um installment é criado, gera automaticamente as transações

CREATE OR REPLACE FUNCTION generate_installment_transactions()
RETURNS TRIGGER AS $$
DECLARE
v_installment_number INTEGER;
    v_transaction_date DATE;
    v_invoice_id UUID;
    v_billing_month DATE;
BEGIN
    -- Gerar cada parcela
FOR v_installment_number IN 1..NEW.total_installments LOOP
        -- Calcular data da transação (primeiro vencimento + offset de meses)
        v_transaction_date := NEW.first_due_date + ((v_installment_number - 1) || ' months')::INTERVAL;

        -- Encontrar a fatura correspondente ao mês da parcela
        v_billing_month := DATE_TRUNC('month', v_transaction_date);

SELECT id INTO v_invoice_id
FROM invoices
WHERE card_id = NEW.card_id
  AND billing_month = v_billing_month
  AND deleted_at IS NULL
    LIMIT 1;

-- Se não encontrou fatura, criar uma nova
IF v_invoice_id IS NULL THEN
            INSERT INTO invoices (
                id,
                card_id,
                billing_month,
                closing_date,
                due_date,
                status,
                created_at,
                created_by
            )
SELECT
    gen_random_uuid(),
    NEW.card_id,
    v_billing_month,
    DATE_TRUNC('month', v_billing_month) + (c.closing_day - 1 || ' days')::INTERVAL,
    DATE_TRUNC('month', v_billing_month) + '1 month'::INTERVAL + (c.due_day - 1 || ' days')::INTERVAL,
    'OPEN',
    CURRENT_TIMESTAMP,
    NEW.created_by
FROM cards c
WHERE c.id = NEW.card_id
    RETURNING id INTO v_invoice_id;
END IF;

        -- Criar a transação
INSERT INTO transactions (
    id,
    user_id,
    description,
    amount,
    transaction_date,
    transaction_type,
    payment_type,
    invoice_id,
    installment_id,
    installment_number,
    created_at,
    created_by
)
SELECT
    gen_random_uuid(),
    ba.user_id,
    NEW.description || ' (' || v_installment_number || '/' || NEW.total_installments || ')',
    NEW.installment_value,
    v_transaction_date,
    'EXPENSE',
    'CREDIT',
    v_invoice_id,
    NEW.id,
    v_installment_number,
    CURRENT_TIMESTAMP,
    NEW.created_by
FROM cards c
         INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
WHERE c.id = NEW.card_id;

END LOOP;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_installment_transactions
    AFTER INSERT ON installments
    FOR EACH ROW
    WHEN (NEW.deleted_at IS NULL)
    EXECUTE FUNCTION generate_installment_transactions();

COMMENT ON FUNCTION generate_installment_transactions() IS
'Gera automaticamente todas as transações quando um parcelamento é criado';

CREATE OR REPLACE FUNCTION generate_all_recurring_transactions()
RETURNS TRIGGER AS $$
DECLARE
v_current_date DATE;
    v_end_date DATE;
    v_invoice_id UUID;
    v_billing_month DATE;
    v_iteration INTEGER := 0;
    v_max_iterations INTEGER := 1200; -- Máximo de 100 anos (proteção contra loop infinito)
BEGIN
    -- Definir data de início
    v_current_date := NEW.start_date;

    -- Definir data fim (se null, gera apenas próximos 24 meses)
    IF NEW.end_date IS NULL THEN
        v_end_date := CURRENT_DATE + INTERVAL '24 months';
ELSE
        v_end_date := NEW.end_date;
END IF;

    -- Gerar transações baseado na frequência
CASE NEW.frequency

        -- ═══════════════════════════════════════════════════════
        -- FREQUÊNCIA MENSAL
        -- ═══════════════════════════════════════════════════════
        WHEN 'MONTHLY' THEN
            WHILE v_current_date <= v_end_date AND v_iteration < v_max_iterations LOOP

                -- Buscar/criar fatura se for CREDIT
                v_invoice_id := NULL;
                IF NEW.payment_type = 'CREDIT' AND NEW.card_id IS NOT NULL THEN
                    v_billing_month := DATE_TRUNC('month', v_current_date);

SELECT id INTO v_invoice_id
FROM invoices
WHERE card_id = NEW.card_id
  AND billing_month = v_billing_month
  AND deleted_at IS NULL
    LIMIT 1;

-- Se não encontrou fatura, criar uma nova
IF v_invoice_id IS NULL THEN
                        INSERT INTO invoices (
                            id,
                            card_id,
                            billing_month,
                            closing_date,
                            due_date,
                            status,
                            created_at,
                            created_by
                        )
SELECT
    gen_random_uuid(),
    NEW.card_id,
    v_billing_month,
    DATE_TRUNC('month', v_billing_month) + (c.closing_day - 1 || ' days')::INTERVAL,
    DATE_TRUNC('month', v_billing_month) + '1 month'::INTERVAL + (c.due_day - 1 || ' days')::INTERVAL,
    'OPEN',
    CURRENT_TIMESTAMP,
    NEW.created_by
FROM cards c
WHERE c.id = NEW.card_id
    RETURNING id INTO v_invoice_id;
END IF;
END IF;

                -- Criar transação
INSERT INTO transactions (
    id,
    user_id,
    category_id,
    description,
    amount,
    transaction_date,
    transaction_type,
    payment_type,
    bank_account_id,
    invoice_id,
    recurring_transaction_id,
    created_at,
    created_by
) VALUES (
             gen_random_uuid(),
             NEW.user_id,
             NEW.category_id,
             NEW.description,
             NEW.amount,
             v_current_date,
             'EXPENSE',
             NEW.payment_type,
             NEW.bank_account_id,
             v_invoice_id,
             NEW.id,
             CURRENT_TIMESTAMP,
             NEW.created_by
         );

-- Avançar para próximo mês
v_current_date := v_current_date + INTERVAL '1 month';
                v_iteration := v_iteration + 1;
END LOOP;

        -- ═══════════════════════════════════════════════════════
        -- FREQUÊNCIA SEMANAL
        -- ═══════════════════════════════════════════════════════
WHEN 'WEEKLY' THEN
            WHILE v_current_date <= v_end_date AND v_iteration < v_max_iterations LOOP

                -- Buscar/criar fatura se for CREDIT
                v_invoice_id := NULL;
                IF NEW.payment_type = 'CREDIT' AND NEW.card_id IS NOT NULL THEN
                    v_billing_month := DATE_TRUNC('month', v_current_date);

SELECT id INTO v_invoice_id
FROM invoices
WHERE card_id = NEW.card_id
  AND billing_month = v_billing_month
  AND deleted_at IS NULL
    LIMIT 1;
END IF;

                -- Criar transação
INSERT INTO transactions (
    id,
    user_id,
    category_id,
    description,
    amount,
    transaction_date,
    transaction_type,
    payment_type,
    bank_account_id,
    invoice_id,
    recurring_transaction_id,
    created_at,
    created_by
) VALUES (
             gen_random_uuid(),
             NEW.user_id,
             NEW.category_id,
             NEW.description,
             NEW.amount,
             v_current_date,
             'EXPENSE',
             NEW.payment_type,
             NEW.bank_account_id,
             v_invoice_id,
             NEW.id,
             CURRENT_TIMESTAMP,
             NEW.created_by
         );

-- Avançar para próxima semana
v_current_date := v_current_date + INTERVAL '7 days';
                v_iteration := v_iteration + 1;
END LOOP;

        -- ═══════════════════════════════════════════════════════
        -- FREQUÊNCIA QUINZENAL
        -- ═══════════════════════════════════════════════════════
WHEN 'BIWEEKLY' THEN
            WHILE v_current_date <= v_end_date AND v_iteration < v_max_iterations LOOP

                v_invoice_id := NULL;
                IF NEW.payment_type = 'CREDIT' AND NEW.card_id IS NOT NULL THEN
                    v_billing_month := DATE_TRUNC('month', v_current_date);

SELECT id INTO v_invoice_id
FROM invoices
WHERE card_id = NEW.card_id
  AND billing_month = v_billing_month
  AND deleted_at IS NULL
    LIMIT 1;
END IF;

INSERT INTO transactions (
    id,
    user_id,
    category_id,
    description,
    amount,
    transaction_date,
    transaction_type,
    payment_type,
    bank_account_id,
    invoice_id,
    recurring_transaction_id,
    created_at,
    created_by
) VALUES (
             gen_random_uuid(),
             NEW.user_id,
             NEW.category_id,
             NEW.description,
             NEW.amount,
             v_current_date,
             'EXPENSE',
             NEW.payment_type,
             NEW.bank_account_id,
             v_invoice_id,
             NEW.id,
             CURRENT_TIMESTAMP,
             NEW.created_by
         );

-- Avançar 14 dias
v_current_date := v_current_date + INTERVAL '14 days';
                v_iteration := v_iteration + 1;
END LOOP;

        -- ═══════════════════════════════════════════════════════
        -- FREQUÊNCIA ANUAL
        -- ═══════════════════════════════════════════════════════
WHEN 'YEARLY' THEN
            WHILE v_current_date <= v_end_date AND v_iteration < v_max_iterations LOOP

                v_invoice_id := NULL;
                IF NEW.payment_type = 'CREDIT' AND NEW.card_id IS NOT NULL THEN
                    v_billing_month := DATE_TRUNC('month', v_current_date);

SELECT id INTO v_invoice_id
FROM invoices
WHERE card_id = NEW.card_id
  AND billing_month = v_billing_month
  AND deleted_at IS NULL
    LIMIT 1;
END IF;

INSERT INTO transactions (
    id,
    user_id,
    category_id,
    description,
    amount,
    transaction_date,
    transaction_type,
    payment_type,
    bank_account_id,
    invoice_id,
    recurring_transaction_id,
    created_at,
    created_by
) VALUES (
             gen_random_uuid(),
             NEW.user_id,
             NEW.category_id,
             NEW.description,
             NEW.amount,
             v_current_date,
             'EXPENSE',
             NEW.payment_type,
             NEW.bank_account_id,
             v_invoice_id,
             NEW.id,
             CURRENT_TIMESTAMP,
             NEW.created_by
         );

-- Avançar 1 ano
v_current_date := v_current_date + INTERVAL '1 year';
                v_iteration := v_iteration + 1;
END LOOP;
END CASE;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_all_recurring_transactions
    AFTER INSERT ON recurring_transactions
    FOR EACH ROW
    WHEN (NEW.deleted_at IS NULL)
    EXECUTE FUNCTION generate_all_recurring_transactions();

COMMENT ON FUNCTION generate_all_recurring_transactions() IS
'Gera TODAS as transações recorrentes de uma vez quando a recorrência é criada.
Se end_date = NULL, gera apenas próximos 24 meses.';


-- =====================================================
-- 6. TRIGGER: ENVIAR ALERTA DE ORÇAMENTO
-- =====================================================
-- Quando gastos atingem o limite do orçamento, cria notificação

CREATE OR REPLACE FUNCTION check_budget_alert()
RETURNS TRIGGER AS $$
DECLARE
v_budget RECORD;
    v_total_spent NUMERIC;
    v_percentage NUMERIC;
BEGIN
    -- Buscar orçamentos da categoria e mês da transação
FOR v_budget IN
SELECT b.*
FROM budgets b
WHERE b.user_id = NEW.user_id
  AND b.category_id = NEW.category_id
  AND b.budget_month = DATE_TRUNC('month', NEW.transaction_date)
  AND b.deleted_at IS NULL
  AND b.alert_sent = false
    LOOP
-- Calcular total gasto no mês
SELECT COALESCE(SUM(t.amount), 0) INTO v_total_spent
FROM transactions t
WHERE t.user_id = v_budget.user_id
  AND t.category_id = v_budget.category_id
  AND DATE_TRUNC('month', t.transaction_date) = v_budget.budget_month
  AND t.transaction_type = 'EXPENSE'
  AND t.deleted_at IS NULL;

-- Calcular percentual
v_percentage := (v_total_spent / NULLIF(v_budget.planned_amount, 0)) * 100;

        -- Se ultrapassou o threshold, criar notificação
        IF v_percentage >= v_budget.alert_threshold THEN
            INSERT INTO notifications (
                id,
                user_id,
                type,
                title,
                message,
                related_entity_type,
                related_entity_id,
                created_at
            )
SELECT
    gen_random_uuid(),
    v_budget.user_id,
    'BUDGET_ALERT',
    'Orçamento atingindo o limite!',
    'Você já gastou ' || ROUND(v_percentage, 2) || '% do orçamento de ' || c.name || ' (' ||
    'R$ ' || v_total_spent || ' de R$ ' || v_budget.planned_amount || ')',
    'BUDGET',
    v_budget.id,
    CURRENT_TIMESTAMP
FROM categories c
WHERE c.id = v_budget.category_id;

-- Marcar alerta como enviado
UPDATE budgets
SET alert_sent = true,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE id = v_budget.id;
END IF;
END LOOP;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_budget_alert
    AFTER INSERT OR UPDATE ON transactions
                        FOR EACH ROW
                        WHEN (NEW.transaction_type = 'EXPENSE' AND NEW.deleted_at IS NULL)
                        EXECUTE FUNCTION check_budget_alert();

COMMENT ON FUNCTION check_budget_alert() IS
'Verifica se o gasto ultrapassou o threshold do orçamento e cria notificação';

-- =====================================================
-- 7. TRIGGER: ALERTA DE VENCIMENTO DE FATURA
-- =====================================================
-- Cria notificação quando fatura está perto do vencimento (7 dias antes)

CREATE OR REPLACE FUNCTION create_invoice_due_alerts()
RETURNS VOID AS $$
BEGIN
INSERT INTO notifications (
    id,
    user_id,
    type,
    title,
    message,
    related_entity_type,
    related_entity_id,
    created_at
)
SELECT
    gen_random_uuid(),
    ba.user_id,
    'INVOICE_DUE',
    'Fatura de cartão vencendo em breve',
    'A fatura do cartão ' || c.card_brand || ' vence em ' ||
    (i.due_date - CURRENT_DATE) || ' dias (R$ ' ||
    COALESCE(
            (SELECT SUM(t.amount)
             FROM transactions t
             WHERE t.invoice_id = i.id AND t.deleted_at IS NULL),
            0
    ) || ')',
    'INVOICE',
    i.id,
    CURRENT_TIMESTAMP
FROM invoices i
         INNER JOIN cards c ON c.id = i.card_id
         INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
WHERE i.status IN ('OPEN', 'CLOSED')
  AND i.paid_at IS NULL
  AND i.due_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7 days'
  AND i.deleted_at IS NULL
  AND NOT EXISTS (
    SELECT 1
    FROM notifications n
    WHERE n.related_entity_id = i.id
  AND n.type = 'INVOICE_DUE'
  AND n.created_at >= CURRENT_DATE
    );
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION create_invoice_due_alerts() IS
'Cria notificações para faturas que vencem nos próximos 7 dias. Executar via scheduler diário.';

-- =====================================================
-- 8. TRIGGER: ALERTA DE META ATINGIDA
-- =====================================================
-- Quando meta financeira é atingida, criar notificação

CREATE OR REPLACE FUNCTION check_goal_completion()
RETURNS TRIGGER AS $$
DECLARE
v_goal RECORD;
    v_total_contributed NUMERIC;
BEGIN
    -- Buscar a meta associada
SELECT fg.* INTO v_goal
FROM financial_goals fg
         INNER JOIN goal_transactions gt ON gt.goal_id = fg.id
WHERE gt.transaction_id = NEW.id
  AND fg.status = 'IN_PROGRESS'
  AND fg.deleted_at IS NULL;

IF FOUND THEN
        -- Calcular total contribuído
SELECT COALESCE(SUM(t.amount), 0) INTO v_total_contributed
FROM goal_transactions gt
         INNER JOIN transactions t ON t.id = gt.transaction_id
WHERE gt.goal_id = v_goal.id
  AND t.deleted_at IS NULL;

-- Se atingiu a meta
IF v_total_contributed >= v_goal.target_amount THEN
            -- Atualizar status da meta
UPDATE financial_goals
SET status = 'COMPLETED',
    completed_at = CURRENT_TIMESTAMP,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE id = v_goal.id;

-- Criar notificação
INSERT INTO notifications (
    id,
    user_id,
    type,
    title,
    message,
    related_entity_type,
    related_entity_id,
    created_at
) VALUES (
             gen_random_uuid(),
             v_goal.user_id,
             'GOAL_REACHED',
             'Meta financeira atingida! 🎉',
             'Parabéns! Você atingiu a meta "' || v_goal.name || '" de R$ ' ||
             v_goal.target_amount || '!',
             'GOAL',
             v_goal.id,
             CURRENT_TIMESTAMP
         );
END IF;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_goal_completion
    AFTER INSERT ON goal_transactions
    FOR EACH ROW
    EXECUTE FUNCTION check_goal_completion();

COMMENT ON FUNCTION check_goal_completion() IS
'Verifica se meta foi atingida quando uma transação é vinculada e cria notificação';

-- =====================================================
-- 9. TRIGGER: VALIDAR TRANSAÇÃO ANTES DE INSERIR
-- =====================================================
-- Valida regras de negócio antes de criar transação

CREATE OR REPLACE FUNCTION validate_transaction()
RETURNS TRIGGER AS $$
BEGIN
    -- Validar: Transação de crédito deve ter fatura
    IF NEW.payment_type = 'CREDIT' AND NEW.invoice_id IS NULL THEN
        RAISE EXCEPTION 'Transações de crédito devem estar vinculadas a uma fatura';
END IF;

    -- Validar: Transação de débito/transferência deve ter conta
    IF NEW.payment_type IN ('DEBIT', 'TRANSFER') AND NEW.bank_account_id IS NULL THEN
        RAISE EXCEPTION 'Transações de débito/transferência devem estar vinculadas a uma conta bancária';
END IF;

    -- Validar: Valor deve ser positivo
    IF NEW.amount <= 0 THEN
        RAISE EXCEPTION 'O valor da transação deve ser maior que zero';
END IF;

    -- Validar: Se é parcela, deve ter installment_id e installment_number
    IF (NEW.installment_id IS NOT NULL AND NEW.installment_number IS NULL) OR
       (NEW.installment_id IS NULL AND NEW.installment_number IS NOT NULL) THEN
        RAISE EXCEPTION 'Transações parceladas devem ter installment_id e installment_number';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_transaction
    BEFORE INSERT OR UPDATE ON transactions
                         FOR EACH ROW
                         EXECUTE FUNCTION validate_transaction();

COMMENT ON FUNCTION validate_transaction() IS
'Valida regras de negócio das transações antes de inserir/atualizar';

-- =====================================================
-- 10. TRIGGER: IMPEDIR DELEÇÃO FÍSICA DE DADOS
-- =====================================================
-- Força uso de soft delete ao invés de DELETE

CREATE OR REPLACE FUNCTION prevent_hard_delete()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Deleção física não permitida. Use soft delete (UPDATE deleted_at = CURRENT_TIMESTAMP)';
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Aplicar em todas as tabelas principais
CREATE TRIGGER trg_prevent_user_delete
    BEFORE DELETE ON users
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_bank_account_delete
    BEFORE DELETE ON bank_accounts
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_card_delete
    BEFORE DELETE ON cards
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_transaction_delete
    BEFORE DELETE ON transactions
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_invoice_delete
    BEFORE DELETE ON invoices
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_category_delete
    BEFORE DELETE ON categories
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_budget_delete
    BEFORE DELETE ON budgets
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

CREATE TRIGGER trg_prevent_goal_delete
    BEFORE DELETE ON financial_goals
    FOR EACH ROW
    EXECUTE FUNCTION prevent_hard_delete();

COMMENT ON FUNCTION prevent_hard_delete() IS
'Impede deleção física de registros, forçando uso de soft delete';

-- =====================================================
-- FUNÇÕES AUXILIARES PARA SCHEDULER
-- =====================================================

-- Job diário que executa todas as funções de manutenção
CREATE OR REPLACE FUNCTION daily_maintenance_job()
RETURNS VOID AS $$
BEGIN
    -- Fechar faturas vencidas
    PERFORM auto_close_invoices();

    -- Marcar faturas como vencidas
    PERFORM mark_invoices_overdue();

    -- Criar alertas de vencimento
    PERFORM create_invoice_due_alerts();

    RAISE NOTICE 'Daily maintenance job completed at %', CURRENT_TIMESTAMP;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION daily_maintenance_job() IS
'Job de manutenção diária. Executar às 00:00 via pg_cron ou scheduler externo.';

-- =====================================================
-- CONFIGURAÇÃO DO PG_CRON
-- =====================================================

-- 1. Habilitar extensão pg_cron (executar como superuser)
CREATE EXTENSION IF NOT EXISTS pg_cron;

-- 2. Agendar job de manutenção diária (todo dia às 00:00)
SELECT cron.schedule(
               'daily-maintenance-job',
               '0 0 * * *',  -- Cron: minuto hora dia mês dia_semana
               'SELECT daily_maintenance_job()'
       );

-- 3. Agendar fechamento de faturas (todo dia às 01:00)
SELECT cron.schedule(
               'auto-close-invoices',
               '0 1 * * *',
               'SELECT auto_close_invoices()'
       );

-- 4. Agendar marcação de faturas vencidas (todo dia às 02:00)
SELECT cron.schedule(
               'mark-invoices-overdue',
               '0 2 * * *',
               'SELECT mark_invoices_overdue()'
       );


-- 6. Agendar alertas de vencimento (todo dia às 08:00)
SELECT cron.schedule(
               'create-invoice-due-alerts',
               '0 8 * * *',
               'SELECT create_invoice_due_alerts()'
       );

-- 7. Verificar jobs agendados
SELECT * FROM cron.job ORDER BY jobid;

-- 8. Ver histórico de execuções (últimas 100)
SELECT
    jobid,
    runid,
    job_pid,
    database,
    command,
    status,
    return_message,
    start_time,
    end_time
FROM cron.job_run_details
ORDER BY start_time DESC
    LIMIT 100;

-- =====================================================
-- COMANDOS ÚTEIS PARA GERENCIAR JOBS
-- =====================================================

-- Remover um job específico
-- SELECT cron.unschedule('daily-maintenance-job');

-- Remover todos os jobs
-- SELECT cron.unschedule(jobid) FROM cron.job;

-- Alterar schedule de um job
-- SELECT cron.alter_job(
--     job_id := (SELECT jobid FROM cron.job WHERE jobname = 'daily-maintenance-job'),
--     schedule := '0 0 * * *'
-- );

-- Executar job manualmente (para testar)
-- SELECT daily_maintenance_job();

-- Ver logs de execução de um job específico
-- SELECT * FROM cron.job_run_details
-- WHERE jobid = (SELECT jobid FROM cron.job WHERE jobname = 'daily-maintenance-job')
-- ORDER BY start_time DESC
-- LIMIT 10;

-- =====================================================
-- ÍNDICES PARA PERFORMANCE DOS TRIGGERS
-- =====================================================

CREATE INDEX IF NOT EXISTS idx_invoices_status_dates
    ON invoices(status, closing_date, due_date)
    WHERE deleted_at IS NULL;

CREATE INDEX IF NOT EXISTS idx_transactions_user_category_date
    ON transactions(user_id, category_id, transaction_date)
    WHERE deleted_at IS NULL;

CREATE INDEX IF NOT EXISTS idx_recurring_active
    ON recurring_transactions(is_active, frequency, day_of_month)
    WHERE deleted_at IS NULL AND is_active = true;

CREATE INDEX IF NOT EXISTS idx_budgets_alert
    ON budgets(user_id, category_id, budget_month, alert_sent)
    WHERE deleted_at IS NULL AND alert_sent = false;