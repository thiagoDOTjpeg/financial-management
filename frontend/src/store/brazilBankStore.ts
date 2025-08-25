import { create } from "zustand";
import axios from "axios";

export interface BrazilBank {
  ispb: string;
  name: string;
  code: number | null;
  fullName: string;
}

interface BrazilBanksState {
  banks: BrazilBank[];
  loading: boolean;
  error: string | null;
  getBanks: () => Promise<void>;
}

const brasilApi = axios.create({
  baseURL: "https://brasilapi.com.br/api",
});

export const useBrazilBanksStore = create<BrazilBanksState>((set, get) => ({
  banks: [],
  loading: false,
  error: null,
  getBanks: async () => {
    if (get().banks.length > 0) return;

    set({ loading: true, error: null });
    try {
      const response = await brasilApi.get<BrazilBank[]>("/banks/v1");
      set({ banks: response.data, loading: false });
    } catch (error) {
      console.error("Falha ao buscar bancos da BrasilAPI:", error);
      set({ error: "Não foi possível carregar a lista de bancos.", loading: false });
    }
  },
}));