"use client";

import { useFinance } from '../../contexts/FinanceContext';
import Card from '../ui/Card';
import { 
  DollarSign, 
  TrendingUp, 
  TrendingDown, 
  Wallet,
  ArrowUpRight,
  ArrowDownRight
} from 'lucide-react';

export default function Dashboard() {
  const { state } = useFinance();

  // Cálculos do dashboard
  const totalBalance = state.accounts.reduce((sum, account) => sum + account.balance, 0);
  
  const currentMonth = new Date().getMonth();
  const currentYear = new Date().getFullYear();
  
  const monthlyTransactions = state.transactions.filter(transaction => {
    const transactionDate = new Date(transaction.date);
    return transactionDate.getMonth() === currentMonth && 
           transactionDate.getFullYear() === currentYear;
  });

  const monthlyIncome = monthlyTransactions
    .filter(t => t.type === 'income')
    .reduce((sum, t) => sum + Math.abs(t.amount), 0);

  const monthlyExpenses = monthlyTransactions
    .filter(t => t.type === 'expense')
    .reduce((sum, t) => sum + Math.abs(t.amount), 0);

  // Despesas por categoria
  const expensesByCategory = state.categories
    .filter(cat => cat.type === 'expense')
    .map(category => {
      const categoryExpenses = monthlyTransactions
        .filter(t => t.type === 'expense' && t.categoryId === category.id)
        .reduce((sum, t) => sum + Math.abs(t.amount), 0);
      
      return {
        ...category,
        amount: categoryExpenses,
        percentage: monthlyExpenses > 0 ? (categoryExpenses / monthlyExpenses) * 100 : 0
      };
    })
    .filter(cat => cat.amount > 0)
    .sort((a, b) => b.amount - a.amount);

  // Transações recentes
  const recentTransactions = state.transactions
    .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
    .slice(0, 5);

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  return (
    <div className="space-y-6">
      {/* Cards de Resumo */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card>
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <div className="bg-primary-100 p-3 rounded-lg">
                <Wallet className="h-6 w-6 text-primary-600" />
              </div>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Saldo Total</p>
              <p className="text-2xl font-bold text-gray-900">
                {formatCurrency(totalBalance)}
              </p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <div className="bg-success-100 p-3 rounded-lg">
                <TrendingUp className="h-6 w-6 text-success-600" />
              </div>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Receitas do Mês</p>
              <p className="text-2xl font-bold text-success-600">
                {formatCurrency(monthlyIncome)}
              </p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <div className="bg-danger-100 p-3 rounded-lg">
                <TrendingDown className="h-6 w-6 text-danger-600" />
              </div>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Despesas do Mês</p>
              <p className="text-2xl font-bold text-danger-600">
                {formatCurrency(monthlyExpenses)}
              </p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <div className={`p-3 rounded-lg ${
                monthlyIncome - monthlyExpenses >= 0 
                  ? 'bg-success-100' 
                  : 'bg-danger-100'
              }`}>
                <DollarSign className={`h-6 w-6 ${
                  monthlyIncome - monthlyExpenses >= 0 
                    ? 'text-success-600' 
                    : 'text-danger-600'
                }`} />
              </div>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Saldo do Mês</p>
              <p className={`text-2xl font-bold ${
                monthlyIncome - monthlyExpenses >= 0 
                  ? 'text-success-600' 
                  : 'text-danger-600'
              }`}>
                {formatCurrency(monthlyIncome - monthlyExpenses)}
              </p>
            </div>
          </div>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Gráfico de Despesas por Categoria */}
        <Card>
          <h3 className="text-lg font-semibold text-gray-900 mb-4">
            Despesas por Categoria
          </h3>
          
          {expensesByCategory.length > 0 ? (
            <div className="space-y-4">
              {expensesByCategory.map((category) => (
                <div key={category.id} className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <div 
                      className="w-4 h-4 rounded-full"
                      style={{ backgroundColor: category.color }}
                    />
                    <span className="text-sm font-medium text-gray-700">
                      {category.name}
                    </span>
                  </div>
                  <div className="text-right">
                    <p className="text-sm font-semibold text-gray-900">
                      {formatCurrency(category.amount)}
                    </p>
                    <p className="text-xs text-gray-500">
                      {category.percentage.toFixed(1)}%
                    </p>
                  </div>
                </div>
              ))}
              
              {/* Barra de progresso visual */}
              <div className="mt-4">
                <div className="flex h-2 bg-gray-200 rounded-full overflow-hidden">
                  {expensesByCategory.map((category) => (
                    <div
                      key={category.id}
                      className="h-full"
                      style={{
                        backgroundColor: category.color,
                        width: `${category.percentage}%`
                      }}
                    />
                  ))}
                </div>
              </div>
            </div>
          ) : (
            <p className="text-gray-500 text-center py-8">
              Nenhuma despesa registrada este mês
            </p>
          )}
        </Card>

        {/* Transações Recentes */}
        <Card>
          <h3 className="text-lg font-semibold text-gray-900 mb-4">
            Transações Recentes
          </h3>
          
          {recentTransactions.length > 0 ? (
            <div className="space-y-3">
              {recentTransactions.map((transaction) => {
                const account = state.accounts.find(acc => acc.id === transaction.accountId);
                const category = state.categories.find(cat => cat.id === transaction.categoryId);
                
                return (
                  <div key={transaction.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <div className={`p-2 rounded-full ${
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
                          <ArrowUpRight className="h-4 w-4 text-blue-600" />
                        )}
                      </div>
                      <div>
                        <p className="text-sm font-medium text-gray-900">
                          {transaction.description}
                        </p>
                        <p className="text-xs text-gray-500">
                          {category?.name || 'Sem categoria'} • {account?.name}
                        </p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p className={`text-sm font-semibold ${
                        transaction.type === 'income' 
                          ? 'text-success-600' 
                          : 'text-danger-600'
                      }`}>
                        {transaction.type === 'income' ? '+' : ''}
                        {formatCurrency(transaction.amount)}
                      </p>
                      <p className="text-xs text-gray-500">
                        {new Date(transaction.date).toLocaleDateString('pt-BR')}
                      </p>
                    </div>
                  </div>
                );
              })}
            </div>
          ) : (
            <p className="text-gray-500 text-center py-8">
              Nenhuma transação encontrada
            </p>
          )}
        </Card>
      </div>
    </div>
  );
}