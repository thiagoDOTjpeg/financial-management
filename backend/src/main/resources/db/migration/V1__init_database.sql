-- =====================================================
-- SISTEMA DE PLANEJAMENTO FINANCEIRO PESSOAL
-- Modelo Refatorado - Multi-tenancy com Soft Delete
-- =====================================================

-- =====================================================
-- 1. GESTÃO DE USUÁRIOS E AUTENTICAÇÃO
-- =====================================================

CREATE TABLE users (
                       id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,

    -- Status da conta
                       account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                           CHECK (account_status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
                       account_non_expired BOOLEAN NOT NULL DEFAULT true,
                       account_non_locked BOOLEAN NOT NULL DEFAULT true,
                       credentials_non_expired BOOLEAN NOT NULL DEFAULT true,

    -- Auditoria
                       last_login TIMESTAMP(6),
                       created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(100),
                       updated_at TIMESTAMP(6),
                       updated_by VARCHAR(100),
                       deleted_at TIMESTAMP(6)
);

CREATE TABLE roles (
                       id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
                            user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                            granted_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (user_id, role_id)
);

-- =====================================================
-- 2. PLANOS E ASSINATURAS
-- =====================================================

CREATE TABLE subscription_plans (
                                    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                                    name VARCHAR(50) NOT NULL UNIQUE,
                                    display_name VARCHAR(100) NOT NULL,
                                    price NUMERIC(10,2) NOT NULL DEFAULT 0,
                                    billing_cycle VARCHAR(20) NOT NULL DEFAULT 'MONTHLY'
                                        CHECK (billing_cycle IN ('MONTHLY', 'YEARLY')),

    -- Features flags
                                    has_ads BOOLEAN NOT NULL DEFAULT true,
                                    max_bank_accounts INTEGER,
                                    max_cards INTEGER,
                                    has_budgets BOOLEAN NOT NULL DEFAULT false,
                                    has_goals BOOLEAN NOT NULL DEFAULT false,
                                    has_reports BOOLEAN NOT NULL DEFAULT false,
                                    has_recurring_transactions BOOLEAN NOT NULL DEFAULT false,

                                    is_active BOOLEAN NOT NULL DEFAULT true,
                                    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP(6)
);

CREATE TABLE user_subscriptions (
                                    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                                    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                    plan_id UUID NOT NULL REFERENCES subscription_plans(id),

                                    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                                        CHECK (status IN ('ACTIVE', 'CANCELLED', 'EXPIRED', 'SUSPENDED')),

                                    started_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    expires_at TIMESTAMP(6),
                                    cancelled_at TIMESTAMP(6),

                                    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP(6),

                                    CONSTRAINT uk_user_active_subscription UNIQUE (user_id, status)
);

-- =====================================================
-- 3. CONTAS BANCÁRIAS E CARTÕES
-- =====================================================

CREATE TABLE bank_accounts (
                               id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                               bank_name VARCHAR(100) NOT NULL,
                               account_type VARCHAR(20) NOT NULL DEFAULT 'CHECKING'
                                   CHECK (account_type IN ('CHECKING', 'SAVINGS', 'INVESTMENT')),

    -- Dados opcionais
                               account_number VARCHAR(20),
                               agency VARCHAR(10),

                               is_active BOOLEAN NOT NULL DEFAULT true,

    -- Auditoria
                               created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               created_by VARCHAR(100),
                               updated_at TIMESTAMP(6),
                               updated_by VARCHAR(100),
                               deleted_at TIMESTAMP(6)
);

CREATE TABLE cards (
                       id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                       bank_account_id UUID NOT NULL REFERENCES bank_accounts(id) ON DELETE CASCADE,

                       card_brand VARCHAR(50) NOT NULL,
                       card_name VARCHAR(100),
                       last_four_digits VARCHAR(4),

                       credit_limit NUMERIC(12,2) NOT NULL,
                       closing_day INTEGER NOT NULL CHECK (closing_day BETWEEN 1 AND 31),
                       due_day INTEGER NOT NULL CHECK (due_day BETWEEN 1 AND 31),

                       is_active BOOLEAN NOT NULL DEFAULT true,

    -- Auditoria
                       created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(100),
                       updated_at TIMESTAMP(6),
                       updated_by VARCHAR(100),
                       deleted_at TIMESTAMP(6)
);

-- =====================================================
-- 4. CATEGORIAS
-- =====================================================

CREATE TABLE categories (
                            id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                            name VARCHAR(100) NOT NULL,
                            type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
                            icon VARCHAR(50),
                            color VARCHAR(7), -- HEX color

    -- Hierarquia (categoria pai/subcategoria)
                            parent_category_id UUID REFERENCES categories(id) ON DELETE SET NULL,

                            is_active BOOLEAN NOT NULL DEFAULT true,

    -- Auditoria
                            created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(100),
                            updated_at TIMESTAMP(6),
                            updated_by VARCHAR(100),
                            deleted_at TIMESTAMP(6),

                            CONSTRAINT uk_user_category_name UNIQUE (user_id, name, deleted_at)
);

-- =====================================================
-- 5. FATURAS DE CARTÃO
-- =====================================================

CREATE TABLE invoices (
                          id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                          card_id UUID NOT NULL REFERENCES cards(id) ON DELETE CASCADE,

                          billing_month DATE NOT NULL, -- Mês de referência (ex: 2025-10-01)
                          closing_date DATE NOT NULL,
                          due_date DATE NOT NULL,

                          status VARCHAR(20) NOT NULL DEFAULT 'OPEN'
                              CHECK (status IN ('OPEN', 'CLOSED', 'PAID', 'OVERDUE')),

                          paid_at TIMESTAMP(6),

    -- Auditoria
                          created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          created_by VARCHAR(100),
                          updated_at TIMESTAMP(6),
                          updated_by VARCHAR(100),
                          deleted_at TIMESTAMP(6),

                          CONSTRAINT uk_card_billing_month UNIQUE (card_id, billing_month, deleted_at)
);

-- =====================================================
-- 6. PARCELAMENTOS
-- =====================================================

CREATE TABLE installments (
                              id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                              card_id UUID NOT NULL REFERENCES cards(id) ON DELETE CASCADE,

                              description VARCHAR(255) NOT NULL,
                              total_amount NUMERIC(12,2) NOT NULL,
                              total_installments INTEGER NOT NULL CHECK (total_installments > 0),
                              installment_value NUMERIC(12,2) NOT NULL,

                              first_due_date DATE NOT NULL,

    -- Auditoria
                              created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(100),
                              updated_at TIMESTAMP(6),
                              updated_by VARCHAR(100),
                              deleted_at TIMESTAMP(6)
);

-- =====================================================
-- 7. TRANSAÇÕES RECORRENTES (TEMPLATE)
-- =====================================================

CREATE TABLE recurring_transactions (
                                        id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                                        user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                        category_id UUID REFERENCES categories(id) ON DELETE SET NULL,

                                        description VARCHAR(255) NOT NULL,
                                        amount NUMERIC(12,2) NOT NULL,

    -- Tipo de recorrência
                                        frequency VARCHAR(20) NOT NULL
                                            CHECK (frequency IN ('WEEKLY', 'BIWEEKLY', 'MONTHLY', 'YEARLY')),

    -- Dia de vencimento (para mensal/anual)
                                        day_of_month INTEGER CHECK (day_of_month BETWEEN 1 AND 31),
    -- Dia da semana (para semanal)
                                        day_of_week INTEGER CHECK (day_of_week BETWEEN 0 AND 6),

    -- Tipo de pagamento
                                        payment_type VARCHAR(20) NOT NULL
                                            CHECK (payment_type IN ('CREDIT', 'DEBIT', 'TRANSFER')),

    -- Vinculações opcionais
                                        bank_account_id UUID REFERENCES bank_accounts(id) ON DELETE SET NULL,
                                        card_id UUID REFERENCES cards(id) ON DELETE SET NULL,

    -- Período de vigência
                                        start_date DATE NOT NULL,
                                        end_date DATE, -- NULL = infinito

                                        is_active BOOLEAN NOT NULL DEFAULT true,

    -- Auditoria
                                        created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        created_by VARCHAR(100),
                                        updated_at TIMESTAMP(6),
                                        updated_by VARCHAR(100),
                                        deleted_at TIMESTAMP(6)
);

-- =====================================================
-- 8. TRANSAÇÕES (核心)
-- =====================================================

CREATE TABLE transactions (
                              id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                              user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              category_id UUID REFERENCES categories(id) ON DELETE SET NULL,

                              description VARCHAR(255) NOT NULL,
                              amount NUMERIC(12,2) NOT NULL,
                              transaction_date TIMESTAMP(6) NOT NULL,

    -- Tipo de transação
                              transaction_type VARCHAR(10) NOT NULL CHECK (transaction_type IN ('INCOME', 'EXPENSE')),

    -- Tipo de pagamento
                              payment_type VARCHAR(20) NOT NULL
                                  CHECK (payment_type IN ('CREDIT', 'DEBIT', 'TRANSFER', 'CASH')),

    -- Relacionamentos condicionais
                              bank_account_id UUID REFERENCES bank_accounts(id) ON DELETE SET NULL,
                              invoice_id UUID REFERENCES invoices(id) ON DELETE SET NULL,
                              installment_id UUID REFERENCES installments(id) ON DELETE CASCADE,
                              recurring_transaction_id UUID REFERENCES recurring_transactions(id) ON DELETE SET NULL,

    -- Número da parcela (se parcelado)
                              installment_number INTEGER,

    -- Observações
                              notes TEXT,

    -- Auditoria
                              created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(100),
                              updated_at TIMESTAMP(6),
                              updated_by VARCHAR(100),
                              deleted_at TIMESTAMP(6)
);

-- =====================================================
-- 9. ORÇAMENTOS
-- =====================================================

CREATE TABLE budgets (
                         id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                         user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         category_id UUID NOT NULL REFERENCES categories(id) ON DELETE CASCADE,

                         budget_month DATE NOT NULL, -- Mês de referência (ex: 2025-10-01)
                         planned_amount NUMERIC(12,2) NOT NULL,

    -- Alertas
                         alert_threshold INTEGER DEFAULT 80 CHECK (alert_threshold BETWEEN 0 AND 100),
                         alert_sent BOOLEAN NOT NULL DEFAULT false,

    -- Auditoria
                         created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         created_by VARCHAR(100),
                         updated_at TIMESTAMP(6),
                         updated_by VARCHAR(100),
                         deleted_at TIMESTAMP(6),

                         CONSTRAINT uk_user_category_month UNIQUE (user_id, category_id, budget_month, deleted_at)
);

-- =====================================================
-- 10. METAS FINANCEIRAS
-- =====================================================

CREATE TABLE financial_goals (
                                 id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                                 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                                 name VARCHAR(255) NOT NULL,
                                 description TEXT,
                                 target_amount NUMERIC(12,2) NOT NULL,

                                 deadline DATE,

                                 status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS'
                                     CHECK (status IN ('IN_PROGRESS', 'COMPLETED', 'CANCELLED')),

                                 completed_at TIMESTAMP(6),

    -- Auditoria
                                 created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 created_by VARCHAR(100),
                                 updated_at TIMESTAMP(6),
                                 updated_by VARCHAR(100),
                                 deleted_at TIMESTAMP(6)
);

-- Vínculo de transações com metas
CREATE TABLE goal_transactions (
                                   goal_id UUID NOT NULL REFERENCES financial_goals(id) ON DELETE CASCADE,
                                   transaction_id UUID NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
                                   contributed_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (goal_id, transaction_id)
);

-- =====================================================
-- 11. NOTIFICAÇÕES E ALERTAS
-- =====================================================

CREATE TABLE notifications (
                               id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

                               type VARCHAR(50) NOT NULL
                                   CHECK (type IN ('BUDGET_ALERT', 'INVOICE_DUE', 'GOAL_REACHED', 'RECURRING_REMINDER', 'SYSTEM')),

                               title VARCHAR(255) NOT NULL,
                               message TEXT NOT NULL,

    -- Relacionamentos opcionais para contexto
                               related_entity_type VARCHAR(50),
                               related_entity_id UUID,

                               is_read BOOLEAN NOT NULL DEFAULT false,
                               read_at TIMESTAMP(6),

                               created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               deleted_at TIMESTAMP(6)
);

-- =====================================================
-- ÍNDICES PARA PERFORMANCE
-- =====================================================

-- Users
CREATE INDEX idx_users_username ON users(username) WHERE deleted_at IS NULL;
CREATE INDEX idx_users_email ON users(email) WHERE deleted_at IS NULL;

-- Subscriptions
CREATE INDEX idx_user_subscriptions_user_status ON user_subscriptions(user_id, status);

-- Bank Accounts
CREATE INDEX idx_bank_accounts_user ON bank_accounts(user_id) WHERE deleted_at IS NULL;

-- Cards
CREATE INDEX idx_cards_bank_account ON cards(bank_account_id) WHERE deleted_at IS NULL;

-- Categories
CREATE INDEX idx_categories_user ON categories(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_categories_parent ON categories(parent_category_id) WHERE deleted_at IS NULL;

-- Invoices
CREATE INDEX idx_invoices_card ON invoices(card_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_invoices_billing_month ON invoices(billing_month);
CREATE INDEX idx_invoices_status ON invoices(status) WHERE deleted_at IS NULL;

-- Installments
CREATE INDEX idx_installments_card ON installments(card_id) WHERE deleted_at IS NULL;

-- Recurring Transactions
CREATE INDEX idx_recurring_user ON recurring_transactions(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_recurring_active ON recurring_transactions(is_active, start_date, end_date);

-- Transactions (CRÍTICO PARA PERFORMANCE)
CREATE INDEX idx_transactions_user ON transactions(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_transactions_date ON transactions(transaction_date DESC);
CREATE INDEX idx_transactions_user_date ON transactions(user_id, transaction_date DESC) WHERE deleted_at IS NULL;
CREATE INDEX idx_transactions_category ON transactions(category_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_transactions_invoice ON transactions(invoice_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_transactions_installment ON transactions(installment_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_transactions_recurring ON transactions(recurring_transaction_id) WHERE deleted_at IS NULL;

-- Budgets
CREATE INDEX idx_budgets_user_month ON budgets(user_id, budget_month) WHERE deleted_at IS NULL;
CREATE INDEX idx_budgets_category ON budgets(category_id) WHERE deleted_at IS NULL;

-- Financial Goals
CREATE INDEX idx_goals_user ON financial_goals(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_goals_status ON financial_goals(status) WHERE deleted_at IS NULL;

-- Notifications
CREATE INDEX idx_notifications_user_unread ON notifications(user_id, is_read, created_at DESC);

-- =====================================================
-- COMENTÁRIOS NA ESTRUTURA
-- =====================================================

COMMENT ON TABLE users IS 'Usuários do sistema';
COMMENT ON TABLE subscription_plans IS 'Planos de assinatura (Free, Pro)';
COMMENT ON TABLE user_subscriptions IS 'Assinaturas ativas dos usuários';
COMMENT ON TABLE bank_accounts IS 'Contas bancárias dos usuários';
COMMENT ON TABLE cards IS 'Cartões de crédito vinculados às contas';
COMMENT ON TABLE categories IS 'Categorias de receitas/despesas (por usuário)';
COMMENT ON TABLE invoices IS 'Faturas de cartão de crédito';
COMMENT ON TABLE installments IS 'Parcelamentos (template)';
COMMENT ON TABLE recurring_transactions IS 'Transações recorrentes (template)';
COMMENT ON TABLE transactions IS 'Todas as transações financeiras';
COMMENT ON TABLE budgets IS 'Orçamentos mensais por categoria';
COMMENT ON TABLE financial_goals IS 'Metas financeiras dos usuários';
COMMENT ON TABLE notifications IS 'Notificações e alertas do sistema';

-- =====================================================
-- DADOS INICIAIS (SEED)
-- =====================================================

-- Planos padrão
INSERT INTO subscription_plans (id, name, display_name, price, has_ads, has_budgets, has_goals, has_reports, has_recurring_transactions) VALUES
                                                                                                                                             (gen_random_uuid(), 'FREE', 'Plano Gratuito', 0, true, false, false, false, false),
                                                                                                                                             (gen_random_uuid(), 'PRO', 'Plano Pro', 29.90, false, true, true, true, true);

-- Roles padrão
INSERT INTO roles (id, name, description) VALUES
                                              (gen_random_uuid(), 'USER', 'Usuário padrão do sistema'),
                                              (gen_random_uuid(), 'ADMIN', 'Administrador do sistema');