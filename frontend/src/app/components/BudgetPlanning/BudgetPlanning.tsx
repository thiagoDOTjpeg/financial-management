"use client";

import { useState, useEffect } from "react";
import * as S from "./budgetPlanningStyle";
import { FiPlus, FiEdit2, FiTrash2, FiTarget, FiAlertTriangle } from "react-icons/fi";

interface BudgetCategory {
  id: string;
  name: string;
  budgeted: number;
  spent: number;
  type: 'INCOME' | 'EXPENSE';
}

interface Budget {
  id: string;
  month: string;
  year: number;
  categories: BudgetCategory[];
  totalBudgeted: number;
  totalSpent: number;
}

export default function BudgetPlanning() {
  const [currentBudget, setCurrentBudget] = useState<Budget | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [editingCategory, setEditingCategory] = useState<BudgetCategory | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    budgeted: 0,
    type: 'EXPENSE' as 'INCOME' | 'EXPENSE'
  });

  const currentDate = new Date();
  const currentMonth = currentDate.toLocaleString('pt-BR', { month: 'long' });
  const currentYear = currentDate.getFullYear();

  useEffect(() => {
    // Simular carregamento do orçamento atual
    loadCurrentBudget();
  }, []);

  const loadCurrentBudget = () => {
    // Dados simulados - em produção viria da API
    const mockBudget: Budget = {
      id: '1',
      month: currentMonth,
      year: currentYear,
      categories: [
        { id: '1', name: 'Alimentação', budgeted: 800, spent: 650, type: 'EXPENSE' },
        { id: '2', name: 'Transporte', budgeted: 300, spent: 280, type: 'EXPENSE' },
        { id: '3', name: 'Moradia', budgeted: 1200, spent: 1200, type: 'EXPENSE' },
        { id: '4', name: 'Lazer', budgeted: 400, spent: 520, type: 'EXPENSE' },
        { id: '5', name: 'Salário', budgeted: 5000, spent: 5000, type: 'INCOME' },
      ],
      totalBudgeted: 0,
      totalSpent: 0
    };

    // Calcular totais
    mockBudget.totalBudgeted = mockBudget.categories
      .filter(c => c.type === 'EXPENSE')
      .reduce((sum, c) => sum + c.budgeted, 0);
    
    mockBudget.totalSpent = mockBudget.categories
      .filter(c => c.type === 'EXPENSE')
      .reduce((sum, c) => sum + c.spent, 0);

    setCurrentBudget(mockBudget);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!currentBudget) return;

    if (editingCategory) {
      // Editar categoria existente
      const updatedCategories = currentBudget.categories.map(cat =>
        cat.id === editingCategory.id
          ? { ...cat, name: formData.name, budgeted: formData.budgeted, type: formData.type }
          : cat
      );
      
      setCurrentBudget({
        ...currentBudget,
        categories: updatedCategories
      });
    } else {
      // Adicionar nova categoria
      const newCategory: BudgetCategory = {
        id: Date.now().toString(),
        name: formData.name,
        budgeted: formData.budgeted,
        spent: 0,
        type: formData.type
      };
      
      setCurrentBudget({
        ...currentBudget,
        categories: [...currentBudget.categories, newCategory]
      });
    }

    resetForm();
  };

  const resetForm = () => {
    setFormData({ name: '', budgeted: 0, type: 'EXPENSE' });
    setEditingCategory(null);
    setShowForm(false);
  };

  const handleEdit = (category: BudgetCategory) => {
    setFormData({
      name: category.name,
      budgeted: category.budgeted,
      type: category.type
    });
    setEditingCategory(category);
    setShowForm(true);
  };

  const handleDelete = (categoryId: string) => {
    if (!currentBudget) return;
    
    const updatedCategories = currentBudget.categories.filter(cat => cat.id !== categoryId);
    setCurrentBudget({
      ...currentBudget,
      categories: updatedCategories
    });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const getProgressPercentage = (spent: number, budgeted: number) => {
    return budgeted > 0 ? (spent / budgeted) * 100 : 0;
  };

  const getProgressColor = (percentage: number) => {
    if (percentage <= 70) return '#4CAF50';
    if (percentage <= 90) return '#FF9800';
    return '#FF5722';
  };

  const incomeCategories = currentBudget?.categories.filter(c => c.type === 'INCOME') || [];
  const expenseCategories = currentBudget?.categories.filter(c => c.type === 'EXPENSE') || [];
  
  const totalIncome = incomeCategories.reduce((sum, c) => sum + c.budgeted, 0);
  const totalExpenseBudget = expenseCategories.reduce((sum, c) => sum + c.budgeted, 0);
  const totalExpenseSpent = expenseCategories.reduce((sum, c) => sum + c.spent, 0);
  const remainingBudget = totalIncome - totalExpenseSpent;

  if (!currentBudget) {
    return <S.LoadingContainer>Carregando orçamento...</S.LoadingContainer>;
  }

  return (
    <S.Container>
      <S.Header>
        <S.Title>Planejamento Orçamentário - {currentMonth} {currentYear}</S.Title>
        <S.AddButton onClick={() => setShowForm(true)}>
          <FiPlus size={20} />
          Nova Categoria
        </S.AddButton>
      </S.Header>

      <S.SummaryCards>
        <S.SummaryCard>
          <S.CardHeader>
            <FiTarget size={24} color="#4CAF50" />
            <span>Receita Total</span>
          </S.CardHeader>
          <S.CardValue positive={true}>
            {formatCurrency(totalIncome)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiTarget size={24} color="#2196F3" />
            <span>Orçamento Total</span>
          </S.CardHeader>
          <S.CardValue positive={true}>
            {formatCurrency(totalExpenseBudget)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiTarget size={24} color="#FF5722" />
            <span>Gasto Total</span>
          </S.CardHeader>
          <S.CardValue positive={false}>
            {formatCurrency(totalExpenseSpent)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiTarget size={24} color={remainingBudget >= 0 ? "#4CAF50" : "#FF5722"} />
            <span>Saldo Restante</span>
          </S.CardHeader>
          <S.CardValue positive={remainingBudget >= 0}>
            {formatCurrency(remainingBudget)}
          </S.CardValue>
        </S.SummaryCard>
      </S.SummaryCards>

      {showForm && (
        <S.FormOverlay>
          <S.FormContainer>
            <S.FormHeader>
              <h3>{editingCategory ? 'Editar Categoria' : 'Nova Categoria'}</h3>
              <S.CloseButton onClick={resetForm}>×</S.CloseButton>
            </S.FormHeader>
            
            <S.Form onSubmit={handleSubmit}>
              <S.FormGroup>
                <S.Label>Nome da Categoria</S.Label>
                <S.Input
                  type="text"
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  required
                />
              </S.FormGroup>

              <S.FormGroup>
                <S.Label>Tipo</S.Label>
                <S.Select
                  value={formData.type}
                  onChange={(e) => setFormData({ ...formData, type: e.target.value as any })}
                >
                  <option value="EXPENSE">Despesa</option>
                  <option value="INCOME">Receita</option>
                </S.Select>
              </S.FormGroup>

              <S.FormGroup>
                <S.Label>Valor Orçado</S.Label>
                <S.Input
                  type="number"
                  step="0.01"
                  value={formData.budgeted}
                  onChange={(e) => setFormData({ ...formData, budgeted: parseFloat(e.target.value) || 0 })}
                  required
                />
              </S.FormGroup>

              <S.FormActions>
                <S.CancelButton type="button" onClick={resetForm}>
                  Cancelar
                </S.CancelButton>
                <S.SubmitButton type="submit">
                  Salvar
                </S.SubmitButton>
              </S.FormActions>
            </S.Form>
          </S.FormContainer>
        </S.FormOverlay>
      )}

      <S.CategoriesSection>
        <S.SectionTitle>Categorias de Despesas</S.SectionTitle>
        <S.CategoriesList>
          {expenseCategories.map((category) => {
            const percentage = getProgressPercentage(category.spent, category.budgeted);
            const isOverBudget = percentage > 100;
            
            return (
              <S.CategoryItem key={category.id}>
                <S.CategoryHeader>
                  <S.CategoryInfo>
                    <S.CategoryName>{category.name}</S.CategoryName>
                    <S.CategoryValues>
                      {formatCurrency(category.spent)} / {formatCurrency(category.budgeted)}
                    </S.CategoryValues>
                  </S.CategoryInfo>
                  
                  <S.CategoryActions>
                    {isOverBudget && (
                      <S.WarningIcon>
                        <FiAlertTriangle size={16} color="#FF5722" />
                      </S.WarningIcon>
                    )}
                    <S.ActionButton onClick={() => handleEdit(category)}>
                      <FiEdit2 size={16} />
                    </S.ActionButton>
                    <S.ActionButton onClick={() => handleDelete(category.id)}>
                      <FiTrash2 size={16} />
                    </S.ActionButton>
                  </S.CategoryActions>
                </S.CategoryHeader>
                
                <S.ProgressBar>
                  <S.ProgressFill
                    percentage={Math.min(percentage, 100)}
                    color={getProgressColor(percentage)}
                  />
                </S.ProgressBar>
                
                <S.ProgressText>
                  {percentage.toFixed(1)}% do orçamento utilizado
                  {isOverBudget && (
                    <S.OverBudgetText>
                      (Excedeu em {formatCurrency(category.spent - category.budgeted)})
                    </S.OverBudgetText>
                  )}
                </S.ProgressText>
              </S.CategoryItem>
            );
          })}
        </S.CategoriesList>
      </S.CategoriesSection>

      {incomeCategories.length > 0 && (
        <S.CategoriesSection>
          <S.SectionTitle>Categorias de Receitas</S.SectionTitle>
          <S.CategoriesList>
            {incomeCategories.map((category) => (
              <S.CategoryItem key={category.id}>
                <S.CategoryHeader>
                  <S.CategoryInfo>
                    <S.CategoryName>{category.name}</S.CategoryName>
                    <S.CategoryValues>
                      {formatCurrency(category.budgeted)}
                    </S.CategoryValues>
                  </S.CategoryInfo>
                  
                  <S.CategoryActions>
                    <S.ActionButton onClick={() => handleEdit(category)}>
                      <FiEdit2 size={16} />
                    </S.ActionButton>
                    <S.ActionButton onClick={() => handleDelete(category.id)}>
                      <FiTrash2 size={16} />
                    </S.ActionButton>
                  </S.CategoryActions>
                </S.CategoryHeader>
              </S.CategoryItem>
            ))}
          </S.CategoriesList>
        </S.CategoriesSection>
      )}
    </S.Container>
  );
}