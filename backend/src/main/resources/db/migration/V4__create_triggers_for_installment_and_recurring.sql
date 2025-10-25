-- =====================================================
-- 11. TRIGGER: CANCELAR TRANSAÇÕES AO CANCELAR PARCELAMENTO
-- =====================================================
-- Quando um installment é marcado como deletado (soft delete),
-- cancela automaticamente todas as transações vinculadas

CREATE OR REPLACE FUNCTION cancel_installment_transactions()
RETURNS TRIGGER AS $$
DECLARE
v_affected_count INTEGER;
BEGIN
    -- Verifica se o installment foi marcado como deletado (soft delete)
    IF NEW.deleted_at IS NOT NULL AND OLD.deleted_at IS NULL THEN

        -- Soft delete em todas as transações vinculadas a este parcelamento
UPDATE transactions
SET
    deleted_at = NEW.deleted_at,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = NEW.updated_by
WHERE installment_id = NEW.id
  AND deleted_at IS NULL;

-- Contar quantas transações foram canceladas
GET DIAGNOSTICS v_affected_count = ROW_COUNT;

-- Log de auditoria
RAISE NOTICE 'Installment % cancelado. % transações foram canceladas automaticamente.',
            NEW.id, v_affected_count;

        -- Criar notificação para o usuário
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
    'SYSTEM',
    'Parcelamento cancelado',
    'O parcelamento "' || NEW.description || '" foi cancelado. ' ||
    v_affected_count || ' transações foram removidas.',
    'INSTALLMENT',
    NEW.id,
    CURRENT_TIMESTAMP
FROM cards c
         INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
WHERE c.id = NEW.card_id;

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_cancel_installment_transactions
    AFTER UPDATE ON installments
    FOR EACH ROW
    WHEN (NEW.deleted_at IS NOT NULL AND OLD.deleted_at IS NULL)
    EXECUTE FUNCTION cancel_installment_transactions();

COMMENT ON FUNCTION cancel_installment_transactions() IS
'Cancela automaticamente todas as transações vinculadas quando um parcelamento é cancelado (soft delete).
Também cria notificação para o usuário informando o cancelamento.';

-- =====================================================
-- 12. TRIGGER: CANCELAR TRANSAÇÕES AO CANCELAR RECORRÊNCIA
-- =====================================================
-- Quando uma recurring_transaction é cancelada ou inativada,
-- cancela apenas as transações FUTURAS

CREATE OR REPLACE FUNCTION cancel_recurring_transactions()
RETURNS TRIGGER AS $$
DECLARE
v_affected_count INTEGER;
BEGIN
    -- Verifica se foi soft deleted OU desativada
    IF (NEW.deleted_at IS NOT NULL AND OLD.deleted_at IS NULL) OR
       (NEW.is_active = false AND OLD.is_active = true) THEN

        -- Soft delete apenas em transações FUTURAS
UPDATE transactions
SET
    deleted_at = CURRENT_TIMESTAMP,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = COALESCE(NEW.updated_by, 'system')
WHERE recurring_transaction_id = NEW.id
  AND transaction_date > CURRENT_DATE  -- Apenas futuras
  AND deleted_at IS NULL;

GET DIAGNOSTICS v_affected_count = ROW_COUNT;

RAISE NOTICE 'Recorrência % cancelada. % transações futuras foram canceladas.',
            NEW.id, v_affected_count;

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
             NEW.user_id,
             'SYSTEM',
             'Recorrência cancelada',
             'A recorrência "' || NEW.description || '" foi cancelada. ' ||
             v_affected_count || ' transações futuras foram removidas.',
             'RECURRING',
             NEW.id,
             CURRENT_TIMESTAMP
         );

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_cancel_recurring_transactions
    AFTER UPDATE ON recurring_transactions
    FOR EACH ROW
    WHEN (
        (NEW.deleted_at IS NOT NULL AND OLD.deleted_at IS NULL) OR
        (NEW.is_active = false AND OLD.is_active = true)
        )
    EXECUTE FUNCTION cancel_recurring_transactions();

COMMENT ON FUNCTION cancel_recurring_transactions() IS
'Cancela automaticamente apenas as transações FUTURAS quando uma recorrência é cancelada ou desativada.
Transações passadas são mantidas para histórico.';

-- =====================================================
-- 13. TRIGGER: REATIVAR TRANSAÇÕES AO REATIVAR RECORRÊNCIA
-- =====================================================
-- Quando uma recurring_transaction é reativada,
-- restaura as transações futuras que foram canceladas

CREATE OR REPLACE FUNCTION reactivate_recurring_transactions()
RETURNS TRIGGER AS $$
DECLARE
v_affected_count INTEGER;
BEGIN
    -- Verifica se foi reativada
    IF NEW.is_active = true AND OLD.is_active = false AND NEW.deleted_at IS NULL THEN

        -- Restaurar transações futuras
UPDATE transactions
SET
    deleted_at = NULL,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = COALESCE(NEW.updated_by, 'system')
WHERE recurring_transaction_id = NEW.id
  AND transaction_date > CURRENT_DATE
  AND deleted_at IS NOT NULL;

GET DIAGNOSTICS v_affected_count = ROW_COUNT;

RAISE NOTICE 'Recorrência % reativada. % transações futuras foram restauradas.',
            NEW.id, v_affected_count;

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
             NEW.user_id,
             'SYSTEM',
             'Recorrência reativada',
             'A recorrência "' || NEW.description || '" foi reativada. ' ||
             v_affected_count || ' transações futuras foram restauradas.',
             'RECURRING',
             NEW.id,
             CURRENT_TIMESTAMP
         );

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_reactivate_recurring_transactions
    AFTER UPDATE ON recurring_transactions
    FOR EACH ROW
    WHEN (NEW.is_active = true AND OLD.is_active = false AND NEW.deleted_at IS NULL)
    EXECUTE FUNCTION reactivate_recurring_transactions();

COMMENT ON FUNCTION reactivate_recurring_transactions() IS
'Restaura automaticamente as transações futuras quando uma recorrência é reativada.';

-- =====================================================
-- 14. FUNÇÃO AUXILIAR: CANCELAR PARCELAMENTO MANUALMENTE
-- =====================================================
-- Função para ser chamada pela aplicação quando usuário cancela parcelamento

CREATE OR REPLACE FUNCTION cancel_installment(
    p_installment_id UUID,
    p_user_id UUID
)
RETURNS JSON AS $$
DECLARE
v_installment RECORD;
    v_transactions_count INTEGER;
    v_result JSON;
BEGIN
    -- Buscar parcelamento
SELECT * INTO v_installment
FROM installments i
         INNER JOIN cards c ON c.id = i.card_id
         INNER JOIN bank_accounts ba ON ba.id = c.bank_account_id
WHERE i.id = p_installment_id
  AND ba.user_id = p_user_id
  AND i.deleted_at IS NULL;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Parcelamento não encontrado ou você não tem permissão para cancelá-lo';
END IF;

    -- Contar transações que serão canceladas
SELECT COUNT(*) INTO v_transactions_count
FROM transactions
WHERE installment_id = p_installment_id
  AND deleted_at IS NULL;

-- Soft delete do parcelamento (trigger vai cancelar transações)
UPDATE installments
SET
    deleted_at = CURRENT_TIMESTAMP,
    updated_by = p_user_id::VARCHAR
WHERE id = p_installment_id;

-- Retornar resultado
v_result := json_build_object(
        'installmentId', p_installment_id,
        'description', v_installment.description,
        'cancelledTransactions', v_transactions_count,
        'totalAmount', v_installment.total_amount,
        'message', 'Parcelamento cancelado com sucesso'
    );

RETURN v_result;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION cancel_installment(UUID, UUID) IS
'Cancela um parcelamento e todas suas transações.
Uso: SELECT cancel_installment(installment_id, user_id);';

-- =====================================================
-- 15. FUNÇÃO AUXILIAR: CANCELAR RECORRÊNCIA MANUALMENTE
-- =====================================================
-- Função para ser chamada pela aplicação quando usuário cancela recorrência

CREATE OR REPLACE FUNCTION cancel_recurring(
    p_recurring_id UUID,
    p_user_id UUID,
    p_cancel_past_transactions BOOLEAN DEFAULT false
)
RETURNS JSON AS $$
DECLARE
v_recurring RECORD;
    v_transactions_count INTEGER;
    v_result JSON;
BEGIN
    -- Buscar recorrência
SELECT * INTO v_recurring
FROM recurring_transactions
WHERE id = p_recurring_id
  AND user_id = p_user_id
  AND deleted_at IS NULL;

IF NOT FOUND THEN
        RAISE EXCEPTION 'Recorrência não encontrada ou você não tem permissão para cancelá-la';
END IF;

    -- Contar transações que serão canceladas
    IF p_cancel_past_transactions THEN
SELECT COUNT(*) INTO v_transactions_count
FROM transactions
WHERE recurring_transaction_id = p_recurring_id
  AND deleted_at IS NULL;
ELSE
SELECT COUNT(*) INTO v_transactions_count
FROM transactions
WHERE recurring_transaction_id = p_recurring_id
  AND transaction_date > CURRENT_DATE
  AND deleted_at IS NULL;
END IF;

    -- Desativar recorrência (trigger vai cancelar transações futuras)
UPDATE recurring_transactions
SET
    is_active = false,
    deleted_at = CURRENT_TIMESTAMP,
    updated_by = p_user_id::VARCHAR
WHERE id = p_recurring_id;

-- Se solicitado, cancelar também transações passadas
IF p_cancel_past_transactions THEN
UPDATE transactions
SET
    deleted_at = CURRENT_TIMESTAMP,
    updated_by = p_user_id::VARCHAR
WHERE recurring_transaction_id = p_recurring_id
  AND deleted_at IS NULL;
END IF;

    -- Retornar resultado
    v_result := json_build_object(
        'recurringId', p_recurring_id,
        'description', v_recurring.description,
        'cancelledTransactions', v_transactions_count,
        'frequency', v_recurring.frequency,
        'message', CASE
            WHEN p_cancel_past_transactions THEN 'Recorrência e todas as transações canceladas'
            ELSE 'Recorrência cancelada. Transações futuras removidas'
        END
    );

RETURN v_result;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION cancel_recurring(UUID, UUID, BOOLEAN) IS
'Cancela uma recorrência. Por padrão, cancela apenas transações futuras.
Se p_cancel_past_transactions = true, cancela também transações passadas.
Uso: SELECT cancel_recurring(recurring_id, user_id, false);';