import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const accountsApi = createApi({
  reducerPath: "accountsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8800" }),
  endpoints: (builder) => ({
    getAccounts: builder.query({
      query: () => "/accounts",
    }),
    getAccountsById: builder.query({
      query: (id) => `/accounts/${id}`,
    }),
    addAccount: builder.mutation({
      query: (body) => ({
        url: "/accounts",
        method: "POST",
        body: body,
      })
    }),
    updateAccount: builder.mutation({
      query: ({ id, body }) => ({
        url: `/accounts/${id}`,
        method: "PATCH",
        body: body,
      }),
    }),
    createAccount: builder.mutation({
      query: (body) => ({
        url: "/accounts",
        method: "POST",
        body: body,
      }),
    }),
  }),
});

export const { useGetAccountsQuery, useGetAccountsByIdQuery, useAddAccountMutation, useUpdateAccountMutation, useCreateAccountMutation } = accountsApi;