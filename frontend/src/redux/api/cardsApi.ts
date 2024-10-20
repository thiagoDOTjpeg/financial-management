import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const cardsApi = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8800" }),
  reducerPath: "cardsApi",
  endpoints: (builder) => ({
    getCards: builder.query({
      query: () => "/cards",
    }),
    getCardById: builder.query({
      query: (id) => `/cards/${id}`,
    }),
    getCardsInvoices: builder.query({
      query: (id) => `/cards/invoices/${id}`,
    }),
    addCard: builder.mutation({
      query: (body) => ({
        url: "/cards",
        method: "POST",
        body: body,
      }),
    }),
    updateCard: builder.mutation({
      query: ({ id, body }) => ({
        url: `/cards/${id}`,
        method: "PATCH",
        body: body,
      }),
    }),
    deleteCard: builder.mutation({
      query: (id) => ({
        url: `/cards/${id}`,
        method: "DELETE",
      }),
    }),
  }),
});

export const { useGetCardsQuery, useGetCardByIdQuery, useGetCardsInvoicesQuery, useAddCardMutation, useUpdateCardMutation, useDeleteCardMutation } = cardsApi;