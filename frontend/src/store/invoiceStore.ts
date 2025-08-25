// src/store/invoiceStore.ts

import { create } from "zustand";
import { getInvoices, type PaginatedInvoicesResponse } from "@/api/invoiceApi";
import type { Invoice } from "@/types";

interface InvoiceState {
  invoices: Invoice[];
  allInvoices: Invoice[];
  totalPages: number;
  currentPage: number;
  loading: boolean;
  error: string | null;
  fetchInvoices: (page?: number, size?: number) => Promise<void>;
  fetchAllInvoicesForChart: () => Promise<void>;
}

export const useInvoiceStore = create<InvoiceState>((set, get) => ({
  invoices: [],
  allInvoices: [],
  totalPages: 0,
  currentPage: 0,
  loading: false,
  error: null,
  fetchInvoices: async (page = 0, size = 10) => {
    set({ loading: true, error: null });
    try {
      const response: PaginatedInvoicesResponse = await getInvoices(page, size);
      set({
        invoices: response.invoices,
        totalPages: response.totalPages,
        currentPage: response.currentPage,
        loading: false,
      });
    } catch (error) {
      set({ error: "Não foi possível carregar as faturas.", loading: false });
    }
  },
  fetchAllInvoicesForChart: async () => {
    if (get().allInvoices.length > 0) return;
    try {
      const response = await getInvoices(0, 100);
      set({ allInvoices: response.invoices });
    } catch (error) {
      console.error("Falha ao buscar faturas para o gráfico:", error);
    }
  }
}));