import { create } from "zustand";
import type { CardMinimal, CreateCardData } from "@/types";
import { createCard, getCardsByAccountId } from "@/api/cardApi";

interface CardState {
  cardsByAccount: Record<string, CardMinimal[]>;
  loading: boolean;
  error: string | null;
  fetchCardsByAccountId: (accountId: string) => Promise<void>;
  addCardToAccount: (accountId: string, cardData: CreateCardData) => Promise<void>;
}

export const useCardStore = create<CardState>((set, get) => ({
  cardsByAccount: {},
  loading: false,
  error: null,
  fetchCardsByAccountId: async (accountId: string) => {
    set({ loading: true, error: null });
    try {
      const response = await getCardsByAccountId(accountId);
      set((state) => ({
        cardsByAccount: {
          ...state.cardsByAccount,
          [accountId]: response?.cards || [],
        },
        loading: false,
      }));
    } catch (error) {
      set({ error: "Falha ao buscar cartões.", loading: false });
    }
  },
  addCardToAccount: async (accountId: string, cardData: CreateCardData) => {
    try {
      const newCard = await createCard(accountId, cardData);
      set((state) => ({
        cardsByAccount: {
          ...state.cardsByAccount,
          [accountId]: [...(state.cardsByAccount[accountId] || []), newCard],
        },
      }));
    } catch (error) {
      console.error("Falha ao adicionar cartão:", error);
    }
  },
}));