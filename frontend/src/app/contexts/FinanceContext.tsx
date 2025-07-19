"use client";

import React, { createContext, useContext, useReducer, ReactNode } from 'react';

// Types
export interface Account {
  id: string;
  name: string;
  type: 'checking' | 'savings' | 'investment';
  balance: number;
  createdAt: string;
}

export interface Card {
  id: string;
  name: string;
  accountId: string;
  limit?: number;
  createdAt: string;
}

export interface Category {
  id: string;
  name: string;
  type: 'income' | 'expense';
  color: string;
  createdAt: string;
}

export interface Transaction {
  id: string;
  description: string;
  amount: number;
  type: 'income' | 'expense' | 'transfer';
  categoryId?: string;
  accountId: string;
  targetAccountId?: string; // Para transferências
  date: string;
  createdAt: string;
}

export interface FinanceState {
  accounts: Account[];
  cards: Card[];
  categories: Category[];
  transactions: Transaction[];
  currentPage: 'dashboard' | 'accounts' | 'cards' | 'transactions' | 'categories';
  loading: boolean;
  notification: {
    show: boolean;
    message: string;
    type: 'success' | 'error' | 'info';
  };
}

// Actions
type FinanceAction =
  | { type: 'SET_PAGE'; payload: FinanceState['currentPage'] }
  | { type: 'SET_LOADING'; payload: boolean }
  | { type: 'SHOW_NOTIFICATION'; payload: { message: string; type: 'success' | 'error' | 'info' } }
  | { type: 'HIDE_NOTIFICATION' }
  | { type: 'ADD_ACCOUNT'; payload: Account }
  | { type: 'UPDATE_ACCOUNT'; payload: Account }
  | { type: 'DELETE_ACCOUNT'; payload: string }
  | { type: 'ADD_CARD'; payload: Card }
  | { type: 'UPDATE_CARD'; payload: Card }
  | { type: 'DELETE_CARD'; payload: string }
  | { type: 'ADD_CATEGORY'; payload: Category }
  | { type: 'UPDATE_CATEGORY'; payload: Category }
  | { type: 'DELETE_CATEGORY'; payload: string }
  | { type: 'ADD_TRANSACTION'; payload: Transaction }
  | { type: 'UPDATE_TRANSACTION'; payload: Transaction }
  | { type: 'DELETE_TRANSACTION'; payload: string };

// Initial state with sample data
const initialState: FinanceState = {
  accounts: [
    {
      id: '1',
      name: 'Banco do Brasil',
      type: 'checking',
      balance: 5420.50,
      createdAt: '2024-01-15T10:00:00Z'
    },
    {
      id: '2',
      name: 'Nubank',
      type: 'checking',
      balance: 2150.75,
      createdAt: '2024-01-20T14:30:00Z'
    },
    {
      id: '3',
      name: 'Poupança Caixa',
      type: 'savings',
      balance: 8900.00,
      createdAt: '2024-02-01T09:15:00Z'
    }
  ],
  cards: [
    {
      id: '1',
      name: 'Cartão BB Visa',
      accountId: '1',
      limit: 3000,
      createdAt: '2024-01-15T10:30:00Z'
    },
    {
      id: '2',
      name: 'Nubank Mastercard',
      accountId: '2',
      limit: 5000,
      createdAt: '2024-01-20T15:00:00Z'
    }
  ],
  categories: [
    { id: '1', name: 'Alimentação', type: 'expense', color: '#ef4444', createdAt: '2024-01-01T00:00:00Z' },
    { id: '2', name: 'Transporte', type: 'expense', color: '#f59e0b', createdAt: '2024-01-01T00:00:00Z' },
    { id: '3', name: 'Moradia', type: 'expense', color: '#8b5cf6', createdAt: '2024-01-01T00:00:00Z' },
    { id: '4', name: 'Lazer', type: 'expense', color: '#06b6d4', createdAt: '2024-01-01T00:00:00Z' },
    { id: '5', name: 'Salário', type: 'income', color: '#22c55e', createdAt: '2024-01-01T00:00:00Z' },
    { id: '6', name: 'Freelance', type: 'income', color: '#10b981', createdAt: '2024-01-01T00:00:00Z' }
  ],
  transactions: [
    {
      id: '1',
      description: 'Supermercado Extra',
      amount: -245.80,
      type: 'expense',
      categoryId: '1',
      accountId: '1',
      date: '2024-01-25',
      createdAt: '2024-01-25T18:30:00Z'
    },
    {
      id: '2',
      description: 'Salário Janeiro',
      amount: 4500.00,
      type: 'income',
      categoryId: '5',
      accountId: '1',
      date: '2024-01-05',
      createdAt: '2024-01-05T09:00:00Z'
    },
    {
      id: '3',
      description: 'Uber',
      amount: -28.50,
      type: 'expense',
      categoryId: '2',
      accountId: '2',
      date: '2024-01-24',
      createdAt: '2024-01-24T20:15:00Z'
    },
    {
      id: '4',
      description: 'Cinema',
      amount: -45.00,
      type: 'expense',
      categoryId: '4',
      accountId: '2',
      date: '2024-01-23',
      createdAt: '2024-01-23T21:00:00Z'
    },
    {
      id: '5',
      description: 'Transferência para Poupança',
      amount: -1000.00,
      type: 'transfer',
      accountId: '1',
      targetAccountId: '3',
      date: '2024-01-20',
      createdAt: '2024-01-20T16:00:00Z'
    }
  ],
  currentPage: 'dashboard',
  loading: false,
  notification: {
    show: false,
    message: '',
    type: 'info'
  }
};

// Reducer
function financeReducer(state: FinanceState, action: FinanceAction): FinanceState {
  switch (action.type) {
    case 'SET_PAGE':
      return { ...state, currentPage: action.payload };
    
    case 'SET_LOADING':
      return { ...state, loading: action.payload };
    
    case 'SHOW_NOTIFICATION':
      return {
        ...state,
        notification: {
          show: true,
          message: action.payload.message,
          type: action.payload.type
        }
      };
    
    case 'HIDE_NOTIFICATION':
      return {
        ...state,
        notification: { ...state.notification, show: false }
      };
    
    case 'ADD_ACCOUNT':
      return {
        ...state,
        accounts: [...state.accounts, action.payload]
      };
    
    case 'UPDATE_ACCOUNT':
      return {
        ...state,
        accounts: state.accounts.map(account =>
          account.id === action.payload.id ? action.payload : account
        )
      };
    
    case 'DELETE_ACCOUNT':
      return {
        ...state,
        accounts: state.accounts.filter(account => account.id !== action.payload)
      };
    
    case 'ADD_CARD':
      return {
        ...state,
        cards: [...state.cards, action.payload]
      };
    
    case 'UPDATE_CARD':
      return {
        ...state,
        cards: state.cards.map(card =>
          card.id === action.payload.id ? action.payload : card
        )
      };
    
    case 'DELETE_CARD':
      return {
        ...state,
        cards: state.cards.filter(card => card.id !== action.payload)
      };
    
    case 'ADD_CATEGORY':
      return {
        ...state,
        categories: [...state.categories, action.payload]
      };
    
    case 'UPDATE_CATEGORY':
      return {
        ...state,
        categories: state.categories.map(category =>
          category.id === action.payload.id ? action.payload : category
        )
      };
    
    case 'DELETE_CATEGORY':
      return {
        ...state,
        categories: state.categories.filter(category => category.id !== action.payload)
      };
    
    case 'ADD_TRANSACTION':
      return {
        ...state,
        transactions: [...state.transactions, action.payload],
        accounts: state.accounts.map(account => {
          if (account.id === action.payload.accountId) {
            return { ...account, balance: account.balance + action.payload.amount };
          }
          if (action.payload.targetAccountId && account.id === action.payload.targetAccountId) {
            return { ...account, balance: account.balance - action.payload.amount };
          }
          return account;
        })
      };
    
    case 'UPDATE_TRANSACTION':
      return {
        ...state,
        transactions: state.transactions.map(transaction =>
          transaction.id === action.payload.id ? action.payload : transaction
        )
      };
    
    case 'DELETE_TRANSACTION':
      return {
        ...state,
        transactions: state.transactions.filter(transaction => transaction.id !== action.payload)
      };
    
    default:
      return state;
  }
}

// Context
const FinanceContext = createContext<{
  state: FinanceState;
  dispatch: React.Dispatch<FinanceAction>;
} | null>(null);

// Provider
export function FinanceProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(financeReducer, initialState);

  return (
    <FinanceContext.Provider value={{ state, dispatch }}>
      {children}
    </FinanceContext.Provider>
  );
}

// Hook
export function useFinance() {
  const context = useContext(FinanceContext);
  if (!context) {
    throw new Error('useFinance must be used within a FinanceProvider');
  }
  return context;
}