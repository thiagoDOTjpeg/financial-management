"use client";

import { useState } from 'react';
import { useFinance, Transaction } from '../../contexts/FinanceContext';
import Card from '../ui/Card';
import Button from '../ui/Button';
import Modal from '../ui/Modal';
import Input from '../ui/Input';
import Select from '../ui/Select';
import { 
  Plus, 
  ArrowUpRight, 
  ArrowDownRight, 
  ArrowLeftRight,
  Filter,
  Search
} from 'lucide-react';

export default function TransactionsPage() {
  const { state, dispatch } = useFinance();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [activeTab, setActiveTab] = useState<'expense' | 'income' | 'transfer'>('expense');
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState<'all' | 'income' | 'expense' | 'transfer'>('all');
  const [filterMonth, setFilterMonth] = useState('');
  const [formData, setFormData] = useState({
    description: '',
    amount: '',
    categoryId: '',
    accountId: '',
    targetAccountId: '',
    date: new Date().toISOString().split('T')[0]
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleOpenModal = () => {
    setFormData({
      description: '',
      amount: '',
      categoryId: '',
      accountId: '',
      targetAccountId: '',
      date: new Date().toISOString().split('T')[0]
    });
    setErrors({});
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setFormData({
      description: '',
      amount: '',
      categoryId: '',
      accountId: '',
      targetAccountId: '',
      date: new Date().toISOString().split('T')[0]
    });
    setErrors({});
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.description.trim()) {
      newErrors.description = 'Descrição é obrigatória';
    }

    if (!formData.amount.trim()) {
      newErrors.amount = 'Valor é obrigatório';
    } else if (isNaN(Number(formData.amount)) || Number(formData.amount) <= 0) {
      newErrors.amount = 'Valor deve ser um número positivo';
    }

    if (!formData.accountId) {
      newErrors.accountId = 'Conta é obrigatória';
    }

    if (activeTab !== 'transfer' && !formData.categoryId) {
      newErrors.categoryId = 'Categoria é obrigatória';
    }

    if (activeTab === 'transfer' && !formData.targetAccountId) {
      newErrors.targetAccountId = 'Conta de destino é obrigatória';
    }

    if (activeTab === 'transfer' && formData.accountId === formData.targetAccountId) {
      newErrors.targetAccountId = 'Conta de destino deve ser diferente da conta de origem';
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

    const amount = Number(formData.amount);
    const transactionData: Transaction = {
      id: Date.now().toString(),
      description: formData.description.trim(),
      amount: activeTab === 'expense' ? -amount : amount,
      type: activeTab,
      categoryId: activeTab !== 'transfer' ? formData.categoryId : undefined,
      accountId: formData.accountId,
      targetAccountId: activeTab === 'transfer' ? formData.targetAccountId : undefined,
      date: formData.date,
      createdAt: new Date().toISOString()
    };

    dispatch({ type: 'ADD_TRANSACTION', payload: transactionData });
    dispatch({ 
      type: 'SHOW_NOTIFICATION', 
      payload: { message: 'Transação criada com sucesso!', type: 'success' }
    });

    dispatch({ type: 'SET_LOADING', payload: false });
    handleCloseModal();
  };

  // Filtrar transações
  const filteredTransactions = state.transactions.filter(transaction => {
    const matchesSearch = transaction.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesType = filterType === 'all' || transaction.type === filterType;
    
    let matchesMonth = true;
    if (filterMonth) {
      const transactionMonth = new Date(transaction.date).toISOString().slice(0, 7);
      matchesMonth = transactionMonth === filterMonth;
    }

    return matchesSearch && matchesType && matchesMonth;
  }).sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(Math.abs(value));
  };

  const getAccountName = (accountId: string) => {
    const account = state.accounts.find(acc => acc.id === accountId);
    return account?.name || 'Conta não encontrada';
  };

  const getCategoryName = (categoryId?: string) => {
    if (!categoryId) return 'Sem categoria';
    const category = state.categories.find(cat => cat.id === categoryId);
    return category?.name || 'Categoria não encontrada';
  };

  const availableCategories = state.categories.filter(cat => 
    activeTab === 'transfer' ? false : cat.type === activeTab
  );

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h2 className="text-xl font-semibold text-gray-900">Transações</h2>
          <p className="text-gray-600">Gerencie suas receitas, despesas e transferências</p>
        </div>
        <Button
          onClick={handleOpenModal}
          icon={<Plus className="h-4 w-4" />}
          disabled={state.accounts.length === 0}
        >
          Nova Transação
        </Button>
      </div>

      {/* Filtros */}
      <Card>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
            <input
              type="text"
              placeholder="Buscar transações..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10 input-field"
            />
          </div>

          <Select
            value={filterType}
            onChange={(e) => setFilterType(e.target.value as typeof filterType)}
          >
            <option value="all">Todos os tipos</option>
            <option value="income">Receitas</option>
            <option value="expense">Despesas</option>
            <option value="transfer">Transferências</option>
          </Select>

          <Input
            type="month"
            value={filterMonth}
            onChange={(e) => setFilterMonth(e.target.value)}
            placeholder="Filtrar por mês"
          />
        </div>
      </Card>

      {/* Lista de Transações */}
      {filteredTransactions.length > 0 ? (
        <Card padding={false}>
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Transação
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Categoria
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Conta
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Data
                  </th>
                  <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Valor
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredTransactions.map((transaction) => (
                  <tr key={transaction.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center">
                        <div className={`p-2 rounded-full mr-3 ${
                          transaction.type === 'income' 
                            ? 'bg-success-100' 
                            : transaction.type === 'expense'
                            ? 'bg-danger-100'
                            : 'bg-blue-100'
                        }`}>
                          {transaction.type === 'income' ? (
                            <ArrowUpRight className="h-4 w-4 text-success-600" />
                          ) : transaction.type === 'expense' ? (
                            <ArrowDownRight className="h-4 w-4 text-danger-600" />
                          ) : (
                            <ArrowLeftRight className="h-4 w-4 text-blue-600" />
                          )}
                        </div>
                        <div>
                          <div className="text-sm font-medium text-gray-900">
                            {transaction.description}
                          </div>
                          <div className="text-sm text-gray-500">
                            {transaction.type === 'income' ? 'Receita' : 
                             transaction.type === 'expense' ? 'Despesa' : 'Transferência'}
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {getCategoryName(transaction.categoryId)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {getAccountName(transaction.accountId)}
                      {transaction.targetAccountId && (
                        <div className="text-xs text-gray-500">
                          → {getAccountName(transaction.targetAccountId)}
                        </div>
                      )}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {new Date(transaction.date).toLocaleDateString('pt-BR')}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right">
                      <span className={`text-sm font-semibold ${
                        transaction.type === 'income' 
                          ? 'text-success-600' 
                          : transaction.type === 'expense'
                          ? 'text-danger-600'
                          : 'text-blue-600'
                      }`}>
                        {transaction.type === 'income' ? '+' : transaction.type === 'expense' ? '-' : ''}
                        {formatCurrency(transaction.amount)}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </Card>
      ) : (
        <Card className="text-center py-12">
          <ArrowLeftRight className="h-12 w-12 text-gray-400 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">
            Nenhuma transação encontrada
          </h3>
          <p className="text-gray-500 mb-6">
            {searchTerm || filterType !== 'all' || filterMonth
              ? 'Tente ajustar os filtros de busca'
              : 'Comece registrando sua primeira transação'
            }
          </p>
          {!searchTerm && filterType === 'all' && !filterMonth && (
            <Button
              onClick={handleOpenModal}
              icon={<Plus className="h-4 w-4" />}
              disabled={state.accounts.length === 0}
            >
              Adicionar Primeira Transação
            </Button>
          )}
        </Card>
      )}

      {/* Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        title="Nova Transação"
        size="lg"
      >
        {/* Tabs */}
        <div className="flex space-x-1 bg-gray-100 p-1 rounded-lg mb-6">
          <button
            type="button"
            onClick={() => setActiveTab('expense')}
            className={`flex-1 py-2 px-4 rounded-md text-sm font-medium transition-colors ${
              activeTab === 'expense'
                ? 'bg-white text-gray-900 shadow-sm'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            Despesa
          </button>
          <button
            type="button"
            onClick={() => setActiveTab('income')}
            className={`flex-1 py-2 px-4 rounded-md text-sm font-medium transition-colors ${
              activeTab === 'income'
                ? 'bg-white text-gray-900 shadow-sm'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            Receita
          </button>
          <button
            type="button"
            onClick={() => setActiveTab('transfer')}
            className={`flex-1 py-2 px-4 rounded-md text-sm font-medium transition-colors ${
              activeTab === 'transfer'
                ? 'bg-white text-gray-900 shadow-sm'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            Transferência
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <Input
              label="Descrição"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              error={errors.description}
              placeholder={
                activeTab === 'expense' ? 'Ex: Supermercado' :
                activeTab === 'income' ? 'Ex: Salário' :
                'Ex: Transferência para poupança'
              }
              required
            />

            <Input
              label="Valor"
              type="number"
              step="0.01"
              value={formData.amount}
              onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
              error={errors.amount}
              placeholder="0,00"
              required
            />
          </div>

          {activeTab !== 'transfer' && (
            <Select
              label="Categoria"
              value={formData.categoryId}
              onChange={(e) => setFormData({ ...formData, categoryId: e.target.value })}
              error={errors.categoryId}
              required
            >
              <option value="">Selecione uma categoria</option>
              {availableCategories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </Select>
          )}

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <Select
              label={activeTab === 'transfer' ? 'Conta de Origem' : 'Conta'}
              value={formData.accountId}
              onChange={(e) => setFormData({ ...formData, accountId: e.target.value })}
              error={errors.accountId}
              required
            >
              <option value="">Selecione uma conta</option>
              {state.accounts.map((account) => (
                <option key={account.id} value={account.id}>
                  {account.name}
                </option>
              ))}
            </Select>

            {activeTab === 'transfer' && (
              <Select
                label="Conta de Destino"
                value={formData.targetAccountId}
                onChange={(e) => setFormData({ ...formData, targetAccountId: e.target.value })}
                error={errors.targetAccountId}
                required
              >
                <option value="">Selecione uma conta</option>
                {state.accounts
                  .filter(account => account.id !== formData.accountId)
                  .map((account) => (
                    <option key={account.id} value={account.id}>
                      {account.name}
                    </option>
                  ))}
              </Select>
            )}

            <Input
              label="Data"
              type="date"
              value={formData.date}
              onChange={(e) => setFormData({ ...formData, date: e.target.value })}
              required
            />
          </div>

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
              Criar Transação
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}