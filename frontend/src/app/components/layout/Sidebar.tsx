"use client";

import { useFinance } from '../../contexts/FinanceContext';
import { 
  LayoutDashboard, 
  Wallet, 
  CreditCard, 
  ArrowLeftRight, 
  Tags,
  DollarSign
} from 'lucide-react';

const menuItems = [
  { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard },
  { id: 'accounts', label: 'Contas', icon: Wallet },
  { id: 'cards', label: 'Cartões', icon: CreditCard },
  { id: 'transactions', label: 'Transações', icon: ArrowLeftRight },
  { id: 'categories', label: 'Categorias', icon: Tags },
];

export default function Sidebar() {
  const { state, dispatch } = useFinance();

  const handlePageChange = (page: typeof state.currentPage) => {
    dispatch({ type: 'SET_PAGE', payload: page });
  };

  return (
    <div className="bg-white w-64 shadow-lg flex flex-col">
      {/* Logo */}
      <div className="p-6 border-b border-gray-200">
        <div className="flex items-center space-x-3">
          <div className="bg-primary-600 p-2 rounded-lg">
            <DollarSign className="h-6 w-6 text-white" />
          </div>
          <div>
            <h1 className="text-xl font-bold text-gray-900">FinanceApp</h1>
            <p className="text-sm text-gray-500">Suas finanças organizadas</p>
          </div>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 p-4">
        <ul className="space-y-2">
          {menuItems.map((item) => {
            const Icon = item.icon;
            const isActive = state.currentPage === item.id;
            
            return (
              <li key={item.id}>
                <button
                  onClick={() => handlePageChange(item.id as typeof state.currentPage)}
                  className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg text-left transition-colors duration-200 ${
                    isActive
                      ? 'bg-primary-50 text-primary-700 border-r-2 border-primary-600'
                      : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
                  }`}
                >
                  <Icon className="h-5 w-5" />
                  <span className="font-medium">{item.label}</span>
                </button>
              </li>
            );
          })}
        </ul>
      </nav>

      {/* User Info */}
      <div className="p-4 border-t border-gray-200">
        <div className="flex items-center space-x-3">
          <div className="bg-gray-300 rounded-full h-10 w-10 flex items-center justify-center">
            <span className="text-gray-600 font-medium">U</span>
          </div>
          <div>
            <p className="text-sm font-medium text-gray-900">Usuário</p>
            <p className="text-xs text-gray-500">user@email.com</p>
          </div>
        </div>
      </div>
    </div>
  );
}