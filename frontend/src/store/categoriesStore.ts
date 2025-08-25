
import { create } from "zustand";
import type { Category, CreateCategoryData } from "@/types";
import { createCategory, getCategories, type PaginatedCategoriesResponse } from "@/api/categoriesApi";

interface CategoryState {
  categories: Category[];
  totalPages: number;
  currentPage: number;
  loading: boolean;
  error: string | null;
  fetchCategories: (page?: number, size?: number) => Promise<void>;
  addCategory: (categoryData: CreateCategoryData) => Promise<void>;
}

export const useCategoryStore = create<CategoryState>((set, get) => ({
  categories: [],
  totalPages: 0,
  currentPage: 0,
  loading: false,
  error: null,
  fetchCategories: async (page = 0, size = 10) => {
    set({ loading: true, error: null });
    try {
      const response: PaginatedCategoriesResponse = await getCategories(page, size);
      set({
        categories: response.categories,
        totalPages: response.totalPages,
        currentPage: response.currentPage,
        loading: false,
      });
    } catch (error) {
      console.error("Falha ao buscar categorias:", error);
      set({ error: "Não foi possível carregar as categorias.", loading: false });
    }
  },
  addCategory: async (categoryData: CreateCategoryData) => {
    try {
      await createCategory(categoryData);
      await get().fetchCategories(get().currentPage);
    } catch (error) {
      console.error("Falha ao adicionar categoria:", error);
    }
  },
}));