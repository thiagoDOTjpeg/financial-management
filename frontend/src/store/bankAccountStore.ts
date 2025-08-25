import { getBankAccounts, createBankAccount } from "@/api/bankAccountsApi";
import type { BankAccount, CreateBankAccountData } from "@/types";
import { create } from "zustand";

interface BankAccountState {
  bankAccounts: BankAccount[];
  loading: boolean;
  error: string | null;
  getBankAccounts: () => Promise<void>;
  createBankAccount: (bankAccount: CreateBankAccountData) => Promise<void>;
}

export const useBankAccountStore = create<BankAccountState>((set) => ({
  bankAccounts: [],
  loading: false,
  error: null,
  getBankAccounts: async () => {
    set({ loading: true, error: null })
    try {
      const bankAccounts = await getBankAccounts();
      set({ bankAccounts, loading: false });
    } catch (error) {
      console.error("Erro ao buscar contas bancárias:", error);
      set({ error: "Erro ao buscar contas bancárias" })
    } finally {
      set({ loading: false });
    }
  },
  createBankAccount: async (accountData: CreateBankAccountData) => {
    try {
      const newAccount = await createBankAccount(accountData);
      set((state) => ({
        bankAccounts: [...state.bankAccounts, newAccount],
      }));
    } catch (error) {
      console.error("Falha ao adicionar conta bancária:", error);
    }
  }
}));