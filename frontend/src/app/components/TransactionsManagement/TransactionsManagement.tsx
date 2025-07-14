"use client";

import { useState } from "react";
import { useGetTransactionsQuery, useAddTransactionMutation } from "@/redux/api/transactionsApi";
import { useGetAccountsQuery } from "@/redux/api/accountsApi";
import * as S from "./transactionsManagementStyle";
import { FiPlus, FiFilter, FiSearch, FiTrendingUp, FiTrendingDown } from "react-icons/fi";

interface Transaction {
  id?: string;
  description: string;
  amount: number;
  type: 'INCOME' | 'EXPENSE';
  category: string;
  accountId: string;
  date: string;
}

export default function TransactionsManagement() {
  const [showForm, setShowForm] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState<'ALL' | 'INCOME' | 'EXPENSE'>('ALL');
  const [formData, setFormData] = useState<Transaction>({
    description: '',
    amount: 0,
    type: 'EXPENSE',
    category: '',
    accountId: '',
    date: new Date().toISOString().split('T')[0]
  });

  const { data: transactions, isLoading, refetch } = useGetTransactionsQuery({});
  const { data: accounts } = useGetAccountsQuery({});
  const [addTransaction, { isLoading: isAdding }] = useAddTransactionMutation();

  const categories = {
    INCOME: ['Salário', 'Freelance', 'Investimentos', 'Outros'],
    EXPENSE: ['Alimentação', 'Transporte', 'Moradia', 'Saúde', 'Educação', 'Lazer', 'Outros']
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      await addTransaction(formData).unwrap();
      resetForm();
      refetch();
    } catch (error) {
      console.error('Erro ao adicionar transação:', error);
    }
  };

  const resetForm = () => {
    setFormData({
      description: '',
      amount: 0,
      type: 'EXPENSE',
      category: '',
      accountId: '',
      date: new Date().toISOString().split('T')[0]
    });
    setShowForm(false);
  };

  const filteredTransactions = transactions?.filter((transaction: any) => {
    const matchesSearch = transaction.description?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         transaction.category?.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesType = filterType === 'ALL' || transaction.type === filterType;
    return matchesSearch && matchesType;
  }) || [];

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  if (isLoading) {
    return <S.LoadingContainer>Carregando transações...</S.LoadingContainer>;
  }

  return (
    <S.Container>
      <S.Header>
        <S.Title>Gerenciamento de Transações</S.Title>
        <S.AddButton onClick={() => setShowForm(true)}>
          <FiPlus size={20} />
          Nova Transação
        </S.AddButton>
      </S.Header>

      <S.FiltersContainer>
        <S.SearchContainer>
          <FiSearch size={20} />
          <S.SearchInput
            type="text"
            placeholder="Buscar transações..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </S.SearchContainer>

        <S.FilterContainer>
          <FiFilter size={20} />
          <S.FilterSelect
            value={filterType}
            onChange={(e) => setFilterType(e.target.value as any)}
          >
            <option value="ALL">Todas</option>
            <option value="INCOME">Receitas</option>
            <option value="EXPENSE">Despesas</option>
          </S.FilterSelect>
        </S.FilterContainer>
      </S.FiltersContainer>

      {showForm && (
        <S.FormOverlay>
          <S.FormContainer>
            <S.FormHeader>
              <h3>Nova Transação</h3>
              <S.CloseButton onClick={resetForm}>×</S.CloseButton>
            </S.FormHeader>
            
            <S.Form onSubmit={handleSubmit}>
              <S.FormRow>
                <S.FormGroup>
                  <S.Label>Descrição</S.Label>
                  <S.Input
                    type="text"
                    value={formData.description}
                    onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                    required
                  />
                </S.FormGroup>

                <S.FormGroup>
                  <S.Label>Tipo</S.Label>
                  <S.Select
                    value={formData.type}
                    onChange={(e) => setFormData({ ...formData, type: e.target.value as any, category: '' })}
                  >
                    <option value="EXPENSE">Despesa</option>
                    <option value="INCOME">Receita</option>
                  </S.Select>
                </S.FormGroup>
              </S.FormRow>

              <S.FormRow>
                <S.FormGroup>
                  <S.Label>Valor</S.Label>
                  <S.Input
                    type="number"
                    step="0.01"
                    value={formData.amount}
                    onChange={(e) => setFormData({ ...formData, amount: parseFloat(e.target.value) || 0 })}
                    required
                  />
                </S.FormGroup>

                <S.FormGroup>
                  <S.Label>Data</S.Label>
                  <S.Input
                    type="date"
                    value={formData.date}
                    onChange={(e) => setFormData({ ...formData, date: e.target.value })}
                    required
                  />
                </S.FormGroup>
              </S.FormRow>

              <S.FormRow>
                <S.FormGroup>
                  <S.Label>Categoria</S.Label>
                  <S.Select
                    value={formData.category}
                    onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                    required
                  >
                    <option value="">Selecione uma categoria</option>
                    {categories[formData.type].map((category) => (
                      <option key={category} value={category}>{category}</option>
                    ))}
                  </S.Select>
                </S.FormGroup>

                <S.FormGroup>
                  <S.Label>Conta</S.Label>
                  <S.Select
                    value={formData.accountId}
                    onChange={(e) => setFormData({ ...formData, accountId: e.target.value })}
                    required
                  >
                    <option value="">Selecione uma conta</option>
                    {accounts?.map((account: any) => (
                      <option key={account.id} value={account.id}>
                        {account.bankName}
                      </option>
                    ))}
                  </S.Select>
                </S.FormGroup>
              </S.FormRow>

              <S.FormActions>
                <S.CancelButton type="button" onClick={resetForm}>
                  Cancelar
                </S.CancelButton>
                <S.SubmitButton type="submit" disabled={isAdding}>
                  {isAdding ? 'Salvando...' : 'Salvar'}
                </S.SubmitButton>
              </S.FormActions>
            </S.Form>
          </S.FormContainer>
        </S.FormOverlay>
      )}

      <S.TransactionsContainer>
        <S.TransactionsList>
          {filteredTransactions.map((transaction: any) => (
            <S.TransactionItem key={transaction.id}>
              <S.TransactionIcon type={transaction.type}>
                {transaction.type === 'INCOME' ? <FiTrendingUp /> : <FiTrendingDown />}
              </S.TransactionIcon>
              
              <S.TransactionInfo>
                <S.TransactionDescription>{transaction.description}</S.TransactionDescription>
                <S.TransactionDetails>
                  <span>{transaction.category}</span>
                  <span>•</span>
                  <span>{new Date(transaction.date).toLocaleDateString('pt-BR')}</span>
                </S.TransactionDetails>
              </S.TransactionInfo>
              
              <S.TransactionAmount type={transaction.type}>
                {transaction.type === 'INCOME' ? '+' : '-'}
                {formatCurrency(Math.abs(transaction.amount))}
              </S.TransactionAmount>
            </S.TransactionItem>
          ))}
        </S.TransactionsList>

        {filteredTransactions.length === 0 && (
          <S.EmptyState>
            <FiTrendingUp size={48} color="#ccc" />
            <p>Nenhuma transação encontrada</p>
            <span>
              {searchTerm || filterType !== 'ALL' 
                ? 'Tente ajustar os filtros de busca' 
                : 'Clique em "Nova Transação" para começar'
              }
            </span>
          </S.EmptyState>
        )}
      </S.TransactionsContainer>
    </S.Container>
  );
}