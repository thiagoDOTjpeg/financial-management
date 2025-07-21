"use client";

import { useState } from 'react';
import { useFinance, Card as CardType } from '../../contexts/FinanceContext';
import Card from '../ui/Card';
import Button from '../ui/Button';
import Modal from '../ui/Modal';
import Input from '../ui/Input';
import Select from '../ui/Select';
import { Plus, CreditCard, Edit, Trash2 } from 'lucide-react';

export default function CardsPage() {
  const { state, dispatch } = useFinance();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCard, setEditingCard] = useState<CardType | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    accountId: '',
    limit: ''
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleOpenModal = (card?: CardType) => {
    if (card) {
      setEditingCard(card);
      setFormData({
        name: card.name,
        accountId: card.accountId,
        limit: card.limit?.toString() || ''
      });
    } else {
      setEditingCard(null);
      setFormData({
        name: '',
        accountId: '',
        limit: ''
      });
    }
    setErrors({});
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingCard(null);
    setFormData({ name: '', accountId: '', limit: '' });
    setErrors({});
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Nome do cartão é obrigatório';
    }

    if (!formData.accountId) {
      newErrors.accountId = 'Conta vinculada é obrigatória';
    }

    if (formData.limit && isNaN(Number(formData.limit))) {
      newErrors.limit = 'Limite deve ser um número válido';
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

    const cardData: CardType = {
      id: editingCard?.id || Date.now().toString(),
      name: formData.name.trim(),
      accountId: formData.accountId,
      limit: formData.limit ? Number(formData.limit) : undefined,
      createdAt: editingCard?.createdAt || new Date().toISOString()
    };

    if (editingCard) {
      dispatch({ type: 'UPDATE_CARD', payload: cardData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Cartão atualizado com sucesso!', type: 'success' }
      });
    } else {
      dispatch({ type: 'ADD_CARD', payload: cardData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Cartão criado com sucesso!', type: 'success' }
      });
    }

    dispatch({ type: 'SET_LOADING', payload: false });
    handleCloseModal();
  };

  const handleDelete = async (cardId: string) => {
    if (!confirm('Tem certeza que deseja excluir este cartão?')) return;

    dispatch({ type: 'SET_LOADING', payload: true });
    
    // Simular chamada à API
    await new Promise(resolve => setTimeout(resolve, 500));
    
    dispatch({ type: 'DELETE_CARD', payload: cardId });
    dispatch({ 
      type: 'SHOW_NOTIFICATION', 
      payload: { message: 'Cartão excluído com sucesso!', type: 'success' }
    });
    
    dispatch({ type: 'SET_LOADING', payload: false });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const getAccountName = (accountId: string) => {
    const account = state.accounts.find(acc => acc.id === accountId);
    return account?.name || 'Conta não encontrada';
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h2 className="text-xl font-semibold text-gray-900">Seus Cartões</h2>
          <p className="text-gray-600">Gerencie seus cartões de crédito e débito</p>
        </div>
        <Button
          onClick={() => handleOpenModal()}
          icon={<Plus className="h-4 w-4" />}
          disabled={state.accounts.length === 0}
        >
          Adicionar Cartão
        </Button>
      </div>

      {/* Aviso se não há contas */}
      {state.accounts.length === 0 && (
        <Card className="text-center py-8 bg-yellow-50 border-yellow-200">
          <p className="text-yellow-800">
            Você precisa ter pelo menos uma conta cadastrada para criar cartões.
          </p>
        </Card>
      )}

      {/* Lista de Cartões */}
      {state.cards.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {state.cards.map((card) => (
            <Card key={card.id} className="hover:shadow-md transition-shadow">
              <div className="flex items-start justify-between">
                <div className="flex items-center space-x-3">
                  <div className="bg-blue-100 p-3 rounded-lg">
                    <CreditCard className="h-6 w-6 text-blue-600" />
                  </div>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900">
                      {card.name}
                    </h3>
                    <p className="text-sm text-gray-500">
                      {getAccountName(card.accountId)}
                    </p>
                  </div>
                </div>
                
                <div className="flex space-x-1">
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleOpenModal(card)}
                    icon={<Edit className="h-4 w-4" />}
                    className="!p-2"
                  />
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleDelete(card.id)}
                    icon={<Trash2 className="h-4 w-4" />}
                    className="!p-2 hover:!bg-red-50 hover:!border-red-300 hover:!text-red-600"
                  />
                </div>
              </div>
              
              <div className="mt-4">
                {card.limit && (
                  <p className="text-lg font-semibold text-gray-900">
                    Limite: {formatCurrency(card.limit)}
                  </p>
                )}
                <p className="text-sm text-gray-500 mt-1">
                  Criado em {new Date(card.createdAt).toLocaleDateString('pt-BR')}
                </p>
              </div>
            </Card>
          ))}
        </div>
      ) : state.accounts.length > 0 ? (
        <Card className="text-center py-12">
          <CreditCard className="h-12 w-12 text-gray-400 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">
            Nenhum cartão cadastrado
          </h3>
          <p className="text-gray-500 mb-6">
            Adicione seus cartões para melhor controle financeiro
          </p>
          <Button
            onClick={() => handleOpenModal()}
            icon={<Plus className="h-4 w-4" />}
          >
            Adicionar Primeiro Cartão
          </Button>
        </Card>
      ) : null}

      {/* Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        title={editingCard ? 'Editar Cartão' : 'Novo Cartão'}
      >
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Nome do Cartão"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            error={errors.name}
            placeholder="Ex: Cartão Nubank"
            required
          />

          <Select
            label="Conta Vinculada"
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

          <Input
            label="Limite (Opcional)"
            type="number"
            step="0.01"
            value={formData.limit}
            onChange={(e) => setFormData({ ...formData, limit: e.target.value })}
            error={errors.limit}
            placeholder="0,00"
            helperText="Deixe em branco se não for cartão de crédito"
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
              {editingCard ? 'Atualizar' : 'Criar'} Cartão
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}