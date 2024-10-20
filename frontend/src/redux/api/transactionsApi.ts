import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const transactionsApi = createApi({
  reducerPath: "transactionsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8800" }),
  endpoints: (builder) => ({
    getTransactions: builder.query({
      query: () => "/transactions",
    }),
    getTransactionsById: builder.query({
      query: (id) => `/transactions/${id}`,
    }),
    addTransaction: builder.mutation({
      query: (body) => ({
        url: "/transactions",
        method: "POST",
        body: body,
      })
    }),
    updateTransaction: builder.mutation({
      query: ({ id, body }) => ({
        url: `/transactions/${id}`,
        method: "PATCH",
        body: body,
      }),
    }),
    createTransaction: builder.mutation({
      query: (body) => ({
        url: "/transactions",
        method: "POST",
        body: body,
      }),
    }),
  }),
});

export const { useGetTransactionsQuery, useGetTransactionsByIdQuery, useAddTransactionMutation, useUpdateTransactionMutation, useCreateTransactionMutation } = transactionsApi;