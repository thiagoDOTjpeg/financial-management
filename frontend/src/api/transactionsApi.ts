
import apiClient from './';
import type { Transaction } from '../types';

export interface PaginatedTransactionsResponse {
  transactions: Transaction[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
}

export const getTransactions = async (page = 0, size = 10): Promise<PaginatedTransactionsResponse> => {
  const response = await apiClient.get(`/api/v1/transactions?page=${page}&size=${size}&sort=timestamp,desc`);

  const data = response.data;
  const transactions = data._embedded?.transactions || [];

  const pageInfo = data.page || { totalPages: 0, totalElements: 0, number: 0 };

  return {
    transactions,
    totalPages: pageInfo.totalPages,
    totalElements: pageInfo.totalElements,
    currentPage: pageInfo.number,
  };
};