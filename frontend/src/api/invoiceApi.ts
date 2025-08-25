import apiClient from './';
import type { Invoice } from '../types';

export interface PaginatedInvoicesResponse {
  invoices: Invoice[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
}

export const getInvoices = async (page = 0, size = 10): Promise<PaginatedInvoicesResponse> => {
  const response = await apiClient.get(`/api/v1/invoices?page=${page}&size=${size}&sort=billingMonth,desc`);

  const data = response.data;

  const invoices = data._embedded?.invoice || [];
  const pageInfo = data.page || { totalPages: 0, totalElements: 0, number: 0 };

  return {
    invoices,
    totalPages: pageInfo.totalPages,
    totalElements: pageInfo.totalElements,
    currentPage: pageInfo.number,
  };
};