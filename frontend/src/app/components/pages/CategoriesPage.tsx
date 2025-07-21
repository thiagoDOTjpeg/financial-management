"use client";

import { useState } from 'react';
import { useFinance, Category } from '../../contexts/FinanceContext';
import Card from '../ui/Card';
import Button from '../ui/Button';
import Modal from '../ui/Modal';
import Input from '../ui/Input';
import Select from '../ui/Select';
import { Plus, Tag, Edit, Trash2 } from 'lucide-react';

const colorOptions = [
  { value: '#ef4444', label: 'Vermelho' },
  { value: '#f59e0b', label: 'Laranja' },
  { value: '#eab308', label: 'Amarelo' },
  { value: '#22c55e', label: 'Verde' },
  { value: '#06b6d4', label: 'Azul Claro' },
  { value: '#3b82f6', label: 'Azul' },
  { value: '#8b5cf6', label: 'Roxo' },
  { value: '#ec4899', label: 'Rosa' },
  { value: '#6b7280', label: 'Cinza' },
];

export default function CategoriesPage() {
  const { state, dispatch } = useFinance();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCategory, setEditingCategory] = useState<Category | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    type: 'expense' as Category['type'],
    color: '#ef4444'
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleOpenModal = (category?: Category) => {
    if (category) {
      setEditingCategory(category);
      setFormData({
        name: category.name,
        type: category.type,
        color: category.color
      });
    } else {
      setEditingCategory(null);
      setFormData({
        name: '',
        type: 'expense',
        color: '#ef4444'
      });
    }
    setErrors({});
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingCategory(null);
    setFormData({ name: '', type: 'expense', color: '#ef4444' });
    setErrors({});
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Nome da categoria é obrigatório';
    }

    // Verificar se já existe uma categoria com o mesmo nome e tipo
    const existingCategory = state.categories.find(cat => 
      cat.name.toLowerCase() === formData.name.trim().toLowerCase() && 
      cat.type === formData.type &&
      cat.id !== editingCategory?.id
    );

    if (existingCategory) {
      newErrors.name = 'Já existe uma categoria com este nome para este tipo';
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

    const categoryData: Category = {
      id: editingCategory?.id || Date.now().toString(),
      name: formData.name.trim(),
      type: formData.type,
      color: formData.color,
      createdAt: editingCategory?.createdAt || new Date().toISOString()
    };

    if (editingCategory) {
      dispatch({ type: 'UPDATE_CATEGORY', payload: categoryData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Categoria atualizada com sucesso!', type: 'success' }
      });
    } else {
      dispatch({ type: 'ADD_CATEGORY', payload: categoryData });
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { message: 'Categoria criada com sucesso!', type: 'success' }
      });
    }

    dispatch({ type: 'SET_LOADING', payload: false });
    handleCloseModal();
  };

  const handleDelete = async (categoryId: string) => {
    // Verificar se a categoria está sendo usada em transações
    const isUsed = state.transactions.some(transaction => transaction.categoryId === categoryId);
    
    if (isUsed) {
      dispatch({ 
        type: 'SHOW_NOTIFICATION', 
        payload: { 
          message: 'Não é possível excluir uma categoria que possui transações vinculadas', 
          type: 'error' 
        }
      });
      return;
    }

    if (!confirm('Tem certeza que deseja excluir esta categoria?')) return;

    dispatch({ type: 'SET_LOADING', payload: true });
    
    // Simular chamada à API
    await new Promise(resolve => setTimeout(resolve, 500));
    
    dispatch({ type: 'DELETE_CATEGORY', payload: categoryId });
    dispatch({ 
      type: 'SHOW_NOTIFICATION', 
      payload: { message: 'Categoria excluída com sucesso!', type: 'success' }
    });
    
    dispatch({ type: 'SET_LOADING', payload: false });
  };

  const incomeCategories = state.categories.filter(cat => cat.type === 'income');
  const expenseCategories = state.categories.filter(cat => cat.type === 'expense');

  const getCategoryUsageCount = (categoryId: string) => {
    return state.transactions.filter(transaction => transaction.categoryId === categoryId).length;
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <div>
          <h2 className="text-xl font-semibold text-gray-900">Categorias</h2>
          <p className="text-gray-600">Organize suas transações por categorias</p>
        </div>
        <Button
          onClick={() => handleOpenModal()}
          icon={<Plus className="h-4 w-4" />}
        >
          Criar Categoria
        </Button>
      </div>

      {/* Categorias de Receita */}
      {incomeCategories.length > 0 && (
        <div>
          <h3 className="text-lg font-medium text-gray-900 mb-4">Receitas</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {incomeCategories.map((category) => (
              <Card key={category.id} className="hover:shadow-md transition-shadow">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <div 
                      className="w-4 h-4 rounded-full"
                      style={{ backgroundColor: category.color }}
                    />
                    <div>
                      <h4 className="font-medium text-gray-900">{category.name}</h4>
                      <p className="text-sm text-gray-500">
                        {getCategoryUsageCount(category.id)} transações
                      </p>
                    </div>
                  </div>
                  
                  <div className="flex space-x-1">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleOpenModal(category)}
                      icon={<Edit className="h-4 w-4" />}
                      className="!p-2"
                    />
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleDelete(category.id)}
                      icon={<Trash2 className="h-4 w-4" />}
                      className="!p-2 hover:!bg-red-50 hover:!border-red-300 hover:!text-red-600"
                    />
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </div>
      )}

      {/* Categorias de Despesa */}
      {expenseCategories.length > 0 && (
        <div>
          <h3 className="text-lg font-medium text-gray-900 mb-4">Despesas</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {expenseCategories.map((category) => (
              <Card key={category.id} className="hover:shadow-md transition-shadow">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <div 
                      className="w-4 h-4 rounded-full"
                      style={{ backgroundColor: category.color }}
                    />
                    <div>
                      <h4 className="font-medium text-gray-900">{category.name}</h4>
                      <p className="text-sm text-gray-500">
                        {getCategoryUsageCount(category.id)} transações
                      </p>
                    </div>
                  </div>
                  
                  <div className="flex space-x-1">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleOpenModal(category)}
                      icon={<Edit className="h-4 w-4" />}
                      className="!p-2"
                    />
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleDelete(category.id)}
                      icon={<Trash2 className="h-4 w-4" />}
                      className="!p-2 hover:!bg-red-50 hover:!border-red-300 hover:!text-red-600"
                    />
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </div>
      )}

      {/* Estado vazio */}
      {state.categories.length === 0 && (
        <Card className="text-center py-12">
          <Tag className="h-12 w-12 text-gray-400 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">
            Nenhuma categoria cadastrada
          </h3>
          <p className="text-gray-500 mb-6">
            Crie categorias para organizar melhor suas transações
          </p>
          <Button
            onClick={() => handleOpenModal()}
            icon={<Plus className="h-4 w-4" />}
          >
            Criar Primeira Categoria
          </Button>
        </Card>
      )}

      {/* Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        title={editingCategory ? 'Editar Categoria' : 'Nova Categoria'}
      >
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Nome da Categoria"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            error={errors.name}
            placeholder="Ex: Alimentação"
            required
          />

          <Select
            label="Tipo"
            value={formData.type}
            onChange={(e) => setFormData({ ...formData, type: e.target.value as Category['type'] })}
            required
          >
            <option value="expense">Despesa</option>
            <option value="income">Receita</option>
          </Select>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Cor <span className="text-red-500">*</span>
            </label>
            <div className="grid grid-cols-3 gap-3">
              {colorOptions.map((color) => (
                <button
                  key={color.value}
                  type="button"
                  onClick={() => setFormData({ ...formData, color: color.value })}
                  className={`flex items-center space-x-2 p-3 rounded-lg border-2 transition-colors ${
                    formData.color === color.value
                      ? 'border-gray-400 bg-gray-50'
                      : 'border-gray-200 hover:border-gray-300'
                  }`}
                >
                  <div 
                    className="w-4 h-4 rounded-full"
                    style={{ backgroundColor: color.value }}
                  />
                  <span className="text-sm text-gray-700">{color.label}</span>
                </button>
              ))}
            </div>
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
              {editingCategory ? 'Atualizar' : 'Criar'} Categoria
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}