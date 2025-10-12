-- =====================================================
-- VIEWS ÚTEIS PARA QUERIES FREQUENTES
-- =====================================================

-- =====================================================
-- 1. SALDO POR CONTA BANCÁRIA
-- =====================================================
CREATE VIEW v_bank_account_balances AS
SELECT
    ba.id AS bank_account_id,
    ba.user_id,
    ba.bank_name,
    ba.account_type,
    ba.is_active,
    COALESCE(SUM(
                     CASE
                         WHEN t.transaction_type = 'INCOME' THEN t.amount
                         WHEN t.transaction_type = 'EXPENSE' THEN -t.amount
                         ELSE 0
                         END
             ), 0) AS current_balance,
    COUNT(t.id) AS total_transactions,
    MAX(t.transaction_date) AS last_transaction_date
FROM bank_accounts ba
         LEFT JOIN transactions t ON t.bank_account_id = ba.id
    AND t.payment_type IN ('DEBIT', 'TRANSFER')
    AND t.deleted_at IS NULL
WHERE ba.deleted_at IS NULL
GROUP BY ba.id, ba.user_id, ba.bank_name, ba.account_type, ba.is_active;

COMMENT ON VIEW v_bank_account_balances IS
'Saldo calculado de cada conta bancária com total de transações
Uso: SELECT * FROM v_bank_account_balances WHERE user_id = $1';

-- =====================================================
-- 2. RESUMO FINANCEIRO DO USUÁRIO (DASHBOARD)
-- =====================================================
CREATE VIEW v_user_financial_summary AS
SELECT
    u.id AS user_id,
    u.full_name,
    u.username,

    -- Totais gerais
    COUNT(DISTINCT ba.id) FILTER (WHERE ba.deleted_at IS NULL) AS total_bank_accounts,
    COUNT(DISTINCT c.id) FILTER (WHERE c.deleted_at IS NULL) AS total_cards,
    COUNT(DISTINCT t.id) FILTER (WHERE t.deleted_at IS NULL) AS total_transactions,

    -- Saldo total em contas
    COALESCE(SUM(
                     CASE
                         WHEN t.payment_type IN ('DEBIT', 'TRANSFER') AND t.transaction_type = 'INCOME'
                             THEN t.amount
                         WHEN t.payment_type IN ('DEBIT', 'TRANSFER') AND t.transaction_type = 'EXPENSE'
                             THEN -t.amount
                         ELSE 0
                         END
             ), 0) AS total_balance,

    -- Receitas e despesas do mês atual
    COALESCE(SUM(
                     CASE
                         WHEN t.transaction_type = 'INCOME'
                             AND DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE)
                             THEN t.amount
                         ELSE 0
                         END
             ), 0) AS current_month_income,

    COALESCE(SUM(
                     CASE
                         WHEN t.transaction_type = 'EXPENSE'
                             AND DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE)
                             THEN t.amount
                         ELSE 0
                         END
             ), 0) AS current_month_expenses,

    -- Faturas abertas
    COUNT(DISTINCT i.id) FILTER (WHERE i.status = 'OPEN' AND i.deleted_at IS NULL) AS open_invoices_count,

    -- Metas ativas
    COUNT(DISTINCT fg.id) FILTER (WHERE fg.status = 'IN_PROGRESS' AND fg.deleted_at IS NULL) AS active_goals_count

FROM users u
         LEFT JOIN bank_accounts ba ON ba.user_id = u.id
         LEFT JOIN cards c ON c.bank_account_id = ba.id
         LEFT JOIN transactions t ON t.user_id = u.id AND t.deleted_at IS NULL
         LEFT JOIN invoices i ON i.card_id = c.id
         LEFT JOIN financial_goals fg ON fg.user_id = u.id
WHERE u.deleted_at IS NULL
GROUP BY u.id, u.full_name, u.username;

COMMENT ON VIEW v_user_financial_summary IS
'Resumo financeiro completo do usuário para dashboard
Uso: SELECT * FROM v_user_financial_summary WHERE user_id = $1';

-- =====================================================
-- 3. TOTAIS POR FATURA DE CARTÃO
-- =====================================================
CREATE VIEW v_invoice_details AS
SELECT
    i.id AS invoice_id,
    i.card_id,
    c.card_brand,
    c.card_name,
    ba.user_id,
    i.billing_month,
    i.closing_date,
    i.due_date,
    i.status,
    i.paid_at,

    -- Totais
    COALESCE(SUM(t.amount), 0) AS total_amount,
    COUNT(t.id) AS transaction_count,

    -- Breakdown por tipo
    COUNT(t.id) FILTER (WHERE t.installment_id IS NOT NULL) AS installment_transactions,
    COUNT(t.id) FILTER (WHERE t.installment_id IS NULL) AS single_transactions,

    -- Categoria com mais gasto
    (
        SELECT c2.name
        FROM transactions t2
                 INNER JOIN categories c2 ON c2.id = t2.category_id
        WHERE t2.invoice_id = i.id AND t2.deleted_at IS NULL
        GROUP BY c2.name
        ORDER BY SUM(t2.amount) DESC
        LIMIT 1
    ) AS top_category,

    -- Dias até vencimento
    CASE
        WHEN i.status = 'OPEN' THEN i.due_date - CURRENT_DATE
        ELSE NULL
END AS days_until_due

FROM invoices i
INNER JOIN cards c ON c.id = i.card_id
INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
LEFT JOIN transactions t ON t.invoice_id = i.id AND t.deleted_at IS NULL
WHERE i.deleted_at IS NULL
GROUP BY i.id, i.card_id, c.card_brand, c.card_name, ba.user_id,
         i.billing_month, i.closing_date, i.due_date, i.status, i.paid_at;

COMMENT ON VIEW v_invoice_details IS
'Detalhes completos das faturas com totais e estatísticas
Uso: SELECT * FROM v_invoice_details WHERE user_id = $1 AND billing_month = $2';

-- =====================================================
-- 4. PROGRESSO DE ORÇAMENTOS
-- =====================================================
CREATE VIEW v_budget_progress AS
SELECT
    b.id AS budget_id,
    b.user_id,
    b.category_id,
    c.name AS category_name,
    c.icon AS category_icon,
    c.color AS category_color,
    b.budget_month,
    b.planned_amount,
    b.alert_threshold,
    b.alert_sent,

    -- Valores gastos
    COALESCE(SUM(t.amount), 0) AS spent_amount,
    b.planned_amount - COALESCE(SUM(t.amount), 0) AS remaining_amount,

    -- Percentuais
    ROUND((COALESCE(SUM(t.amount), 0) / NULLIF(b.planned_amount, 0) * 100), 2) AS usage_percentage,

    -- Status
    CASE
        WHEN COALESCE(SUM(t.amount), 0) >= b.planned_amount THEN 'EXCEEDED'
        WHEN COALESCE(SUM(t.amount), 0) >= (b.planned_amount * b.alert_threshold / 100) THEN 'WARNING'
        ELSE 'NORMAL'
        END AS status,

    -- Média diária de gasto
    CASE
        WHEN EXTRACT(DAY FROM CURRENT_DATE) > 0
            THEN ROUND(COALESCE(SUM(t.amount), 0) / EXTRACT(DAY FROM CURRENT_DATE), 2)
        ELSE 0
        END AS daily_average,

    -- Projeção de fim de mês
    CASE
        WHEN EXTRACT(DAY FROM CURRENT_DATE) > 0
            THEN ROUND(
                (COALESCE(SUM(t.amount), 0) / EXTRACT(DAY FROM CURRENT_DATE)) *
                EXTRACT(DAY FROM (DATE_TRUNC('month', b.budget_month) + INTERVAL '1 month - 1 day')),
                2
                 )
        ELSE 0
        END AS projected_month_end

FROM budgets b
         INNER JOIN categories c ON c.id = b.category_id
         LEFT JOIN transactions t ON t.category_id = b.category_id
    AND t.transaction_type = 'EXPENSE'
    AND t.user_id = b.user_id
    AND DATE_TRUNC('month', t.transaction_date) = b.budget_month
    AND t.deleted_at IS NULL
WHERE b.deleted_at IS NULL
GROUP BY b.id, b.user_id, b.category_id, c.name, c.icon, c.color,
         b.budget_month, b.planned_amount, b.alert_threshold, b.alert_sent;

COMMENT ON VIEW v_budget_progress IS
'Progresso detalhado dos orçamentos com projeções e alertas
Uso: SELECT * FROM v_budget_progress WHERE user_id = $1 AND budget_month = DATE_TRUNC(''month'', CURRENT_DATE)';

-- =====================================================
-- 5. PROGRESSO DE METAS FINANCEIRAS
-- =====================================================
CREATE VIEW v_goal_progress AS
SELECT
    g.id AS goal_id,
    g.user_id,
    g.name AS goal_name,
    g.description,
    g.target_amount,
    g.deadline,
    g.status,
    g.completed_at,

    -- Valores aportados
    COALESCE(SUM(t.amount), 0) AS current_amount,
    g.target_amount - COALESCE(SUM(t.amount), 0) AS remaining_amount,

    -- Percentuais
    ROUND((COALESCE(SUM(t.amount), 0) / NULLIF(g.target_amount, 0) * 100), 2) AS progress_percentage,

    -- Estatísticas de aportes
    COUNT(gt.transaction_id) AS total_contributions,
    MAX(t.transaction_date) AS last_contribution_date,
    AVG(t.amount) AS average_contribution,

    -- Análise temporal
    CASE
        WHEN g.deadline IS NOT NULL
            THEN g.deadline - CURRENT_DATE
        ELSE NULL
        END AS days_remaining,

    -- Projeção
    CASE
        WHEN g.deadline IS NOT NULL AND COUNT(gt.transaction_id) > 0
            THEN ROUND(
                (g.target_amount - COALESCE(SUM(t.amount), 0)) /
                NULLIF(EXTRACT(EPOCH FROM (CURRENT_DATE - MIN(t.transaction_date))) / 86400 / COUNT(gt.transaction_id), 0),
                2
                 )
        ELSE NULL
        END AS estimated_daily_contribution_needed

FROM financial_goals g
         LEFT JOIN goal_transactions gt ON gt.goal_id = g.id
         LEFT JOIN transactions t ON t.id = gt.transaction_id AND t.deleted_at IS NULL
WHERE g.deleted_at IS NULL
GROUP BY g.id, g.user_id, g.name, g.description, g.target_amount,
         g.deadline, g.status, g.completed_at;

COMMENT ON VIEW v_goal_progress IS
'Progresso das metas financeiras com estatísticas e projeções
Uso: SELECT * FROM v_goal_progress WHERE user_id = $1 AND status = ''IN_PROGRESS''';

-- =====================================================
-- 6. TRANSAÇÕES COM DETALHES COMPLETOS
-- =====================================================
CREATE VIEW v_transaction_details AS
SELECT
    t.id AS transaction_id,
    t.user_id,
    t.description,
    t.amount,
    t.transaction_date,
    t.transaction_type,
    t.payment_type,
    t.notes,

    -- Categoria
    c.id AS category_id,
    c.name AS category_name,
    c.icon AS category_icon,
    c.color AS category_color,

    -- Conta bancária (se aplicável)
    ba.id AS bank_account_id,
    ba.bank_name,

    -- Cartão (via fatura ou direto)
    COALESCE(card.id, i_card.id) AS card_id,
    COALESCE(card.card_brand, i_card.card_brand) AS card_brand,

    -- Fatura
    i.id AS invoice_id,
    i.billing_month,
    i.status AS invoice_status,

    -- Parcelamento
    inst.id AS installment_id,
    inst.total_installments,
    t.installment_number,
    CONCAT(t.installment_number, '/', inst.total_installments) AS installment_display,

    -- Recorrência
    rt.id AS recurring_transaction_id,
    rt.frequency AS recurring_frequency,

    -- Formatações úteis
    TO_CHAR(t.transaction_date, 'DD/MM/YYYY') AS formatted_date,
    TO_CHAR(t.transaction_date, 'Month YYYY') AS month_year,
    TO_CHAR(t.amount, 'L999G999G999D99') AS formatted_amount

FROM transactions t
         LEFT JOIN categories c ON c.id = t.category_id
         LEFT JOIN bank_accounts ba ON ba.id = t.bank_account_id
         LEFT JOIN invoices i ON i.id = t.invoice_id
         LEFT JOIN cards i_card ON i_card.id = i.card_id
         LEFT JOIN installments inst ON inst.id = t.installment_id
         LEFT JOIN cards card ON card.id = inst.card_id
         LEFT JOIN recurring_transactions rt ON rt.id = t.recurring_transaction_id
WHERE t.deleted_at IS NULL;

COMMENT ON VIEW v_transaction_details IS
'Transações com todos os relacionamentos expandidos
Uso: SELECT * FROM v_transaction_details WHERE user_id = $1 ORDER BY transaction_date DESC LIMIT 50';

-- =====================================================
-- 7. ANÁLISE DE GASTOS POR CATEGORIA
-- =====================================================
CREATE VIEW v_category_spending_analysis AS
SELECT
    c.id AS category_id,
    c.user_id,
    c.name AS category_name,
    c.type AS category_type,
    c.icon,
    c.color,

    -- Mês atual
    COALESCE(SUM(t.amount) FILTER (
        WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE)
    ), 0) AS current_month_total,

    -- Mês anterior
    COALESCE(SUM(t.amount) FILTER (
        WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE - INTERVAL '1 month')
    ), 0) AS previous_month_total,

    -- Últimos 3 meses
    COALESCE(SUM(t.amount) FILTER (
        WHERE t.transaction_date >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '3 months')
    ), 0) AS last_3_months_total,

    -- Últimos 6 meses
    COALESCE(SUM(t.amount) FILTER (
        WHERE t.transaction_date >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '6 months')
    ), 0) AS last_6_months_total,

    -- Total geral
    COALESCE(SUM(t.amount), 0) AS all_time_total,

    -- Estatísticas
    COUNT(t.id) AS total_transactions,
    AVG(t.amount) AS average_transaction,
    MAX(t.amount) AS max_transaction,
    MIN(t.amount) AS min_transaction,
    MAX(t.transaction_date) AS last_transaction_date,

    -- Variação mensal
    CASE
        WHEN SUM(t.amount) FILTER (
            WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE - INTERVAL '1 month')
        ) > 0
        THEN ROUND(
                ((SUM(t.amount) FILTER (
                WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE)
            ) - SUM(t.amount) FILTER (
                WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE - INTERVAL '1 month')
            )) / NULLIF(SUM(t.amount) FILTER (
                    WHERE DATE_TRUNC('month', t.transaction_date) = DATE_TRUNC('month', CURRENT_DATE - INTERVAL '1 month')
            ), 0) * 100),
                2
             )
        ELSE NULL
        END AS month_over_month_change_percent

FROM categories c
         LEFT JOIN transactions t ON t.category_id = c.id
    AND t.user_id = c.user_id
    AND t.deleted_at IS NULL
WHERE c.deleted_at IS NULL
GROUP BY c.id, c.user_id, c.name, c.type, c.icon, c.color;

COMMENT ON VIEW v_category_spending_analysis IS
'Análise de gastos por categoria com comparações temporais
Uso: SELECT * FROM v_category_spending_analysis WHERE user_id = $1 AND category_type = ''EXPENSE'' ORDER BY current_month_total DESC';

-- =====================================================
-- 8. CARTÕES COM LIMITE DISPONÍVEL
-- =====================================================
CREATE VIEW v_card_credit_status AS
SELECT
    c.id AS card_id,
    ba.user_id,
    c.card_brand,
    c.card_name,
    c.last_four_digits,
    c.credit_limit,
    c.closing_day,
    c.due_day,
    c.is_active,

    -- Fatura atual aberta
    i.id AS current_invoice_id,
    i.billing_month AS current_billing_month,
    i.due_date AS current_due_date,

    -- Valores da fatura atual
    COALESCE(SUM(t.amount), 0) AS current_invoice_amount,

    -- Limite disponível
    c.credit_limit - COALESCE(SUM(t.amount), 0) AS available_credit,

    -- Percentual utilizado
    ROUND((COALESCE(SUM(t.amount), 0) / NULLIF(c.credit_limit, 0) * 100), 2) AS credit_usage_percent,

    -- Status
    CASE
        WHEN COALESCE(SUM(t.amount), 0) >= c.credit_limit THEN 'LIMIT_EXCEEDED'
        WHEN COALESCE(SUM(t.amount), 0) >= (c.credit_limit * 0.8) THEN 'HIGH_USAGE'
        WHEN COALESCE(SUM(t.amount), 0) >= (c.credit_limit * 0.5) THEN 'MODERATE_USAGE'
        ELSE 'LOW_USAGE'
        END AS usage_status,

    -- Próxima fatura
    (
        SELECT i2.due_date
        FROM invoices i2
        WHERE i2.card_id = c.id
          AND i2.status = 'OPEN'
          AND i2.due_date > CURRENT_DATE
        ORDER BY i2.due_date ASC
        LIMIT 1
    ) AS next_due_date

FROM cards c
INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
LEFT JOIN invoices i ON i.card_id = c.id
    AND i.status = 'OPEN'
    AND DATE_TRUNC('month', i.billing_month) = DATE_TRUNC('month', CURRENT_DATE)
    AND i.deleted_at IS NULL
LEFT JOIN transactions t ON t.invoice_id = i.id AND t.deleted_at IS NULL
WHERE c.deleted_at IS NULL
GROUP BY c.id, ba.user_id, c.card_brand, c.card_name, c.last_four_digits,
         c.credit_limit, c.closing_day, c.due_day, c.is_active,
         i.id, i.billing_month, i.due_date;

COMMENT ON VIEW v_card_credit_status IS
'Status de crédito dos cartões com limite disponível e utilização
Uso: SELECT * FROM v_card_credit_status WHERE user_id = $1';

-- =====================================================
-- 9. NOTIFICAÇÕES NÃO LIDAS DO USUÁRIO
-- =====================================================
CREATE VIEW v_user_notifications AS
SELECT
    n.id AS notification_id,
    n.user_id,
    n.type,
    n.title,
    n.message,
    n.related_entity_type,
    n.related_entity_id,
    n.is_read,
    n.read_at,
    n.created_at,

    -- Tempo desde criação
    EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - n.created_at)) / 3600 AS hours_ago,

    -- Formatação amigável
    CASE
        WHEN n.created_at > CURRENT_TIMESTAMP - INTERVAL '1 hour' THEN 'Agora há pouco'
        WHEN n.created_at > CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN 'Hoje'
        WHEN n.created_at > CURRENT_TIMESTAMP - INTERVAL '48 hours' THEN 'Ontem'
        ELSE TO_CHAR(n.created_at, 'DD/MM/YYYY')
END AS friendly_date

FROM notifications n
WHERE n.deleted_at IS NULL
ORDER BY n.created_at DESC;

COMMENT ON VIEW v_user_notifications IS
'Notificações do usuário com formatação amigável
Uso: SELECT * FROM v_user_notifications WHERE user_id = $1 AND is_read = false LIMIT 20';

-- =====================================================
-- 10. PRÓXIMOS VENCIMENTOS (FATURAS + RECORRÊNCIAS)
-- =====================================================
CREATE VIEW v_upcoming_payments AS
SELECT
    'INVOICE' AS payment_type,
    i.id AS payment_id,
    ba.user_id,
    c.card_brand || ' - ' || c.card_name AS description,
    (SELECT COALESCE(SUM(t.amount), 0)
     FROM transactions t
     WHERE t.invoice_id = i.id AND t.deleted_at IS NULL) AS amount,
    i.due_date,
    i.due_date - CURRENT_DATE AS days_until_due,
    i.status,
    'CREDIT' AS category
FROM invoices i
         INNER JOIN cards c ON c.id = i.card_id
         INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
WHERE i.status IN ('OPEN', 'CLOSED')
  AND i.due_date >= CURRENT_DATE
  AND i.deleted_at IS NULL

UNION ALL

SELECT
    'RECURRING' AS payment_type,
    rt.id AS payment_id,
    rt.user_id,
    rt.description,
    rt.amount,
    CASE
        WHEN rt.frequency = 'MONTHLY' THEN
            MAKE_DATE(
                    EXTRACT(YEAR FROM CURRENT_DATE)::INTEGER,
                    EXTRACT(MONTH FROM CURRENT_DATE)::INTEGER,
                    rt.day_of_month
            )
        ELSE CURRENT_DATE + INTERVAL '7 days'
END AS due_date,
    CASE
        WHEN rt.frequency = 'MONTHLY' THEN
            MAKE_DATE(
                EXTRACT(YEAR FROM CURRENT_DATE)::INTEGER,
                EXTRACT(MONTH FROM CURRENT_DATE)::INTEGER,
                rt.day_of_month
            ) - CURRENT_DATE
        ELSE 7
END AS days_until_due,
    'RECURRING' AS status,
    rt.payment_type AS category
FROM recurring_transactions rt
WHERE rt.is_active = true
  AND rt.deleted_at IS NULL
  AND (rt.end_date IS NULL OR rt.end_date >= CURRENT_DATE)

ORDER BY due_date ASC;