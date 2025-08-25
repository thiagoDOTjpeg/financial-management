import apiClient from './';
import type { Category, CreateCategoryData } from '../types';

export interface PaginatedCategoriesResponse {
  categories: Category[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
}

export const getCategories = async (page = 0, size = 10): Promise<PaginatedCategoriesResponse> => {
  const response = await apiClient.get(`/api/v1/category?page=${page}&size=${size}&sort=name,asc`);
  const data = response.data;
  const categories = data._embedded?.categories || [];
  const pageInfo = data.page || { totalPages: 0, totalElements: 0, number: 0 };

  return {
    categories,
    totalPages: pageInfo.totalPages,
    totalElements: pageInfo.totalElements,
    currentPage: pageInfo.number,
  };
};

export const createCategory = async (categoryData: CreateCategoryData): Promise<Category> => {
  const response = await apiClient.post('/api/v1/category', categoryData);
  return response.data;
};