import apiClient from "."
import type { Card, CardMinimal, CreateCardData, UserMinimal } from "@/types"

export const getCardsByAccountId = async (accountId: string): Promise<{ id: string, bankName: string, balance: number, user: UserMinimal, cards: CardMinimal[] }> => {
  const response = await apiClient.get(`/api/v1/bankaccount/${accountId}/cards`)
  return response.data || []
}

export const createCard = async (accountId: string, cardData: CreateCardData): Promise<Card> => {
  const response = await apiClient.post(`/api/v1/card?bankAccountId=${accountId}`, cardData)
  return response.data
}