import { create } from "zustand";
import type { Transaction } from "@/types";
import { getTransactions, type PaginatedTransactionsResponse } from "@/api/transactionsApi";

interface TransactionState {
  transactions: Transaction[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
  loading: boolean;
  error: string | null;
  fetchTransactions: (page?: number, size?: number) => Promise<void>;
}

export const useTransactionsStore = create<TransactionState>((set) => ({
  transactions: [],
  totalPages: 0,
  totalElements: 0,
  currentPage: 0,
  loading: false,
  error: null,
  fetchTransactions: async (page = 0, size = 10) => {
    set({ loading: true, error: null });
    try {
      const response: PaginatedTransactionsResponse = await getTransactions(page, size);
      set({
        transactions: response.transactions,
        totalPages: response.totalPages,
        totalElements: response.totalElements,
        currentPage: response.currentPage,
        loading: false,
      });
    } catch (error) {
      console.error("Falha ao buscar transações:", error);
      set({ error: "Não foi possível carregar as transações.", loading: false });
    }
  },
}));