"use client";

import { useFinance } from '../contexts/FinanceContext';
import Sidebar from './layout/Sidebar';
import Header from './layout/Header';
import Dashboard from './pages/Dashboard';
import AccountsPage from './pages/AccountsPage';
import CardsPage from './pages/CardsPage';
import TransactionsPage from './pages/TransactionsPage';
import CategoriesPage from './pages/CategoriesPage';
import Notification from './ui/Notification';
import LoadingSpinner from './ui/LoadingSpinner';

export default function FinanceApp() {
  const { state } = useFinance();

  const renderCurrentPage = () => {
    switch (state.currentPage) {
      case 'dashboard':
        return <Dashboard />;
      case 'accounts':
        return <AccountsPage />;
      case 'cards':
        return <CardsPage />;
      case 'transactions':
        return <TransactionsPage />;
      case 'categories':
        return <CategoriesPage />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="flex h-screen bg-gray-50">
      <Sidebar />
      
      <div className="flex-1 flex flex-col overflow-hidden">
        <Header />
        
        <main className="flex-1 overflow-x-hidden overflow-y-auto bg-gray-50 p-6">
          {state.loading && <LoadingSpinner />}
          {renderCurrentPage()}
        </main>
      </div>
      
      <Notification />
    </div>
  );
}