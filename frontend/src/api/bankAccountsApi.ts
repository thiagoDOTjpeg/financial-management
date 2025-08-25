import type { BankAccount, CreateBankAccountData } from "@/types";
import apiClient from ".";

export const getBankAccounts = async (): Promise<BankAccount[]> => {
  const response = await apiClient.get("/api/v1/bankaccount");
  return response.data._embedded["bank-account"];
}

export const createBankAccount = async (bankAccount: CreateBankAccountData): Promise<BankAccount> => {
  const response = await apiClient.post("/api/v1/bankaccount", bankAccount);
  return response.data;
}
