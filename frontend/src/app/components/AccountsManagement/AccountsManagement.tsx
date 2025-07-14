"use client";

import { useState } from "react";
import { useGetAccountsQuery, useAddAccountMutation, useUpdateAccountMutation } from "@/redux/api/accountsApi";
import * as S from "./accountsManagementStyle";
import { FiPlus, FiEdit2, FiTrash2, FiCreditCard, FiDollarSign } from "react-icons/fi";

interface Account {
  id?: string;
  bankName: string;
  balance: number;
  accountType?: 'CHECKING' | 'SAVINGS' | 'CREDIT_CARD';
}

export default function AccountsManagement() {
  const [showForm, setShowForm] = useState(false);
  const [editingAccount, setEditingAccount] = useState<Account | null>(null);
  const [formData, setFormData] = useState<Account>({
    bankName: '',
    balance: 0,
    accountType: 'CHECKING'
  });

  const { data: accounts, isLoading, refetch } = useGetAccountsQuery({});
  const [addAccount, { isLoading: isAdding }] = useAddAccountMutation();
  const [updateAccount, { isLoading: isUpdating }] = useUpdateAccountMutation();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      if (editingAccount) {
        await updateAccount({
          id: editingAccount.id,
          body: formData
        }).unwrap();
      } else {
        await addAccount(formData).unwrap();
      }
      
      resetForm();
      refetch();
    } catch (error) {
      console.error('Erro ao salvar conta:', error);
    }
  };

  const resetForm = () => {
    setFormData({ bankName: '', balance: 0, accountType: 'CHECKING' });
    setEditingAccount(null);
    setShowForm(false);
  };

  const handleEdit = (account: Account) => {
    setFormData(account);
    setEditingAccount(account);
    setShowForm(true);
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const getAccountTypeLabel = (type: string) => {
    const types = {
      'CHECKING': 'Conta Corrente',
      'SAVINGS': 'Poupança',
      'CREDIT_CARD': 'Cartão de Crédito'
    };
    return types[type as keyof typeof types] || type;
  };

  const getAccountIcon = (type: string) => {
    switch (type) {
      case 'CREDIT_CARD':
        return <FiCreditCard size={20} />;
      default:
        return <FiDollarSign size={20} />;
    }
  };

  if (isLoading) {
    return <S.LoadingContainer>Carregando contas...</S.LoadingContainer>;
  }

  return (
    <S.Container>
      <S.Header>
        <S.Title>Gerenciamento de Contas</S.Title>
        <S.AddButton onClick={() => setShowForm(true)}>
          <FiPlus size={20} />
          Nova Conta
        </S.AddButton>
      </S.Header>

      {showForm && (
        <S.FormOverlay>
          <S.FormContainer>
            <S.FormHeader>
              <h3>{editingAccount ? 'Editar Conta' : 'Nova Conta'}</h3>
              <S.CloseButton onClick={resetForm}>×</S.CloseButton>
            </S.FormHeader>
            
            <S.Form onSubmit={handleSubmit}>
              <S.FormGroup>
                <S.Label>Nome do Banco</S.Label>
                <S.Input
                  type="text"
                  value={formData.bankName}
                  onChange={(e) => setFormData({ ...formData, bankName: e.target.value })}
                  required
                />
              </S.FormGroup>

              <S.FormGroup>
                <S.Label>Tipo de Conta</S.Label>
                <S.Select
                  value={formData.accountType}
                  onChange={(e) => setFormData({ ...formData, accountType: e.target.value as any })}
                >
                  <option value="CHECKING">Conta Corrente</option>
                  <option value="SAVINGS">Poupança</option>
                  <option value="CREDIT_CARD">Cartão de Crédito</option>
                </S.Select>
              </S.FormGroup>

              <S.FormGroup>
                <S.Label>Saldo Inicial</S.Label>
                <S.Input
                  type="number"
                  step="0.01"
                  value={formData.balance}
                  onChange={(e) => setFormData({ ...formData, balance: parseFloat(e.target.value) || 0 })}
                  required
                />
              </S.FormGroup>

              <S.FormActions>
                <S.CancelButton type="button" onClick={resetForm}>
                  Cancelar
                </S.CancelButton>
                <S.SubmitButton type="submit" disabled={isAdding || isUpdating}>
                  {isAdding || isUpdating ? 'Salvando...' : 'Salvar'}
                </S.SubmitButton>
              </S.FormActions>
            </S.Form>
          </S.FormContainer>
        </S.FormOverlay>
      )}

      <S.AccountsGrid>
        {accounts?.map((account: any) => (
          <S.AccountCard key={account.id}>
            <S.AccountHeader>
              <S.AccountIcon>
                {getAccountIcon(account.accountType)}
              </S.AccountIcon>
              <S.AccountInfo>
                <S.AccountName>{account.bankName}</S.AccountName>
                <S.AccountType>{getAccountTypeLabel(account.accountType)}</S.AccountType>
              </S.AccountInfo>
              <S.AccountActions>
                <S.ActionButton onClick={() => handleEdit(account)}>
                  <FiEdit2 size={16} />
                </S.ActionButton>
              </S.AccountActions>
            </S.AccountHeader>
            
            <S.AccountBalance positive={account.balance >= 0}>
              {formatCurrency(account.balance)}
            </S.AccountBalance>
            
            <S.AccountFooter>
              <S.LastUpdated>
                Atualizado em {new Date(account.updatedAt || Date.now()).toLocaleDateString('pt-BR')}
              </S.LastUpdated>
            </S.AccountFooter>
          </S.AccountCard>
        ))}
      </S.AccountsGrid>

      {(!accounts || accounts.length === 0) && (
        <S.EmptyState>
          <FiDollarSign size={48} color="#ccc" />
          <p>Nenhuma conta cadastrada</p>
          <span>Clique em "Nova Conta" para começar</span>
        </S.EmptyState>
      )}
    </S.Container>
  );
}