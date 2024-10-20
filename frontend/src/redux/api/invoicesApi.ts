import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const invoicesApi = createApi({
  reducerPath: "invoicesApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8800" }),
  endpoints: (builder) => ({
    getInvoices: builder.query({
      query: () => "/invoices",
    }),
    getInvoicesById: builder.query({
      query: (id) => `/invoices/${id}`,
    }),
    getInvoicesTransaction: builder.query({
      query: (id) => `/invoices/transactions/${id}`,
    }),
    addInvoice: builder.mutation({
      query: (body) => ({
        url: "/invoices",
        method: "POST",
        body: body,
      })
    }),
    updateInvoice: builder.mutation({
      query: ({ id, body }) => ({
        url: `/invoices/${id}`,
        method: "PATCH",
        body: body,
      }),
    }),
    createInvoice: builder.mutation({
      query: (body) => ({
        url: "/invoices",
        method: "POST",
        body: body,
      }),
    }),
  }),
});

export const { useGetInvoicesQuery, useGetInvoicesByIdQuery, useGetInvoicesTransactionQuery, useAddInvoiceMutation, useUpdateInvoiceMutation, useCreateInvoiceMutation } = invoicesApi;