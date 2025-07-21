"use client";

import { useState } from 'react';
import { useFinance, Account } from '../../contexts/FinanceContext';
import Card from '../ui/Card';
import Button from '../ui/Button';
import Modal from '../ui/Modal';
import Input from '../ui/Input';
import Select from '../ui/Select';
import { Plus, Wallet, Edit, Trash2 } from 'lucide-react';

export default function AccountsPage() {
  const { state, dispatch } = useFinance();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAccount, setEditingAccount] = useState<Account | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    type: 'checking' as Account['type'],
    balance: ''
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleOpenModal = (account?: Account) => {
    if (account) {
      setEditingAccount(account);
      setFormData({
        name: account.name,
        type: account.type,
        balance: account.balance.toString()
      });
    } else {
      setEditingAccount(null);
      setFormData({
        name: '',
        type: 'checking',
        balance: ''
      });
    }
    setErrors({});
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingAccount(null);
    setFormData({ name: '', type: 'checking', balance: '' });
    setErrors({});
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Nome da instituição é obrigatório';
    }

    if (!formData.balance.trim()) {
      newErrors.balance = 'Saldo inicial é obrigatório';
    } else if (isNaN(Number(formData.balance))) {
      newErrors.balance = 'Saldo deve ser um número válido';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) return;

    dispatch({ type: 'SET_LOADING', payload: true });

    // Simular chamada à API
    await new Promise(resolve => setTimeout(resolve, 1000));

    const accountData: Account = {
      id: editingAccount?.id || Date.now().toString(),
      name: formData.name.trim(),
      type: formData.type,
      balance: Number(formData.balance),
      createdAt: editingAccount?.createdAt || new Date().toISOString()
    };

    if (editingAccount) {
      dispatch({ type: 'UPDATE_ACCOUNT', payload: accountData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Conta atualizada com sucesso!', type: 'success' }
      });
    } else {
      dispatch({ type: 'ADD_ACCOUNT', payload: accountData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Conta criada com sucesso!', type: 'success' }
      });
    }

    dispatch({ type: 'SET_LOADING', payload: false });
    handleCloseModal();
  };

  const handleDelete = async (accountId: string) => {
    if (!confirm('Tem certeza que deseja excluir esta conta?')) return;

    dispatch({ type: 'SET_LOADING', payload: true });
    
    // Simular chamada à API
    await new Promise(resolve => setTimeout(resolve, 500));
    
    dispatch({ type: 'DELETE_ACCOUNT', payload: accountId });
    dispatch({ 
      type: 'SHOW_NOTIFICATION', 
      payload: { message: 'Conta excluída com sucesso!', type: 'success' }
    });
    
    dispatch({ type: 'SET_LOADING', payload: false });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const getAccountTypeLabel = (type: Account['type']) => {
    const labels = {
      checking: 'Conta Corrente',
      savings: 'Poupança',
      investment: 'Investimento'
    };
    return labels[type];
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h2 className="text-xl font-semibold text-gray-900">Suas Contas</h2>
          <p className="text-gray-600">Gerencie suas contas bancárias</p>
        </div>
        <Button
          onClick={() => handleOpenModal()}
          icon={<Plus className="h-4 w-4" />}
        >
          Adicionar Conta
        </Button>
      </div>

      {/* Lista de Contas */}
      {state.accounts.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {state.accounts.map((account) => (
            <Card key={account.id} className="hover:shadow-md transition-shadow">
              <div className="flex items-start justify-between">
                <div className="flex items-center space-x-3">
                  <div className="bg-primary-100 p-3 rounded-lg">
                    <Wallet className="h-6 w-6 text-primary-600" />
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">
                      {account.name}
                    </h3>
                    <p className="text-sm text-gray-500">
                      {getAccountTypeLabel(account.type)}
                    </p>
                  </div>
                </div>
                
                <div className="flex space-x-1">
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleOpenModal(account)}
                    icon={<Edit className="h-4 w-4" />}
                    className="!p-2"
                  />
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleDelete(account.id)}
                    icon={<Trash2 className="h-4 w-4" />}
                    className="!p-2 hover:!bg-red-50 hover:!border-red-300 hover:!text-red-600"
                  />
                </div>
              </div>
              
              <div className="mt-4">
                <p className="text-2xl font-bold text-gray-900">
                  {formatCurrency(account.balance)}
                </p>
                <p className="text-sm text-gray-500 mt-1">
                  Criada em {new Date(account.createdAt).toLocaleDateString('pt-BR')}
                </p>
              </div>
            </Card>
          ))}
        </div>
      ) : (
        <Card className="text-center py-12">
          <Wallet className="h-12 w-12 text-gray-400 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">
            Nenhuma conta cadastrada
          </h3>
          <p className="text-gray-500 mb-6">
            Comece adicionando sua primeira conta bancária
          </p>
          <Button
            onClick={() => handleOpenModal()}
            icon={<Plus className="h-4 w-4" />}
          >
            Adicionar Primeira Conta
          </Button>
        </Card>
      )}

      {/* Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        title={editingAccount ? 'Editar Conta' : 'Nova Conta'}
      >
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Nome da Instituição"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            error={errors.name}
            placeholder="Ex: Banco do Brasil"
            required
          />

          <Select
            label="Tipo de Conta"
            value={formData.type}
            onChange={(e) => setFormData({ ...formData, type: e.target.value as Account['type'] })}
            required
          >
            <option value="checking">Conta Corrente</option>
            <option value="savings">Poupança</option>
            <option value="investment">Investimento</option>
          </Select>

          <Input
            label="Saldo Inicial"
            type="number"
            step="0.01"
            value={formData.balance}
            onChange={(e) => setFormData({ ...formData, balance: e.target.value })}
            error={errors.balance}
            placeholder="0,00"
            required
          />

          <div className="flex justify-end space-x-3 pt-4">
            <Button
              type="button"
              variant="outline"
              onClick={handleCloseModal}
            >
              Cancelar
            </Button>
            <Button
              type="submit"
              loading={state.loading}
            >
              {editingAccount ? 'Atualizar' : 'Criar'} Conta
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}