import logger from "redux-logger";
import { configureStore } from "@reduxjs/toolkit";
import { cardsApi } from "../api/cardsApi";
import { accountsApi } from "../api/accountsApi";
import { transactionsApi } from "../api/transactionsApi";
import { invoicesApi } from "../api/invoicesApi";

export const store = configureStore({
  reducer: {
    [cardsApi.reducerPath]: cardsApi.reducer,
    [invoicesApi.reducerPath]: invoicesApi.reducer,
    [accountsApi.reducerPath]: accountsApi.reducer,
    [transactionsApi.reducerPath]: transactionsApi.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware()
    .concat(logger)
    .concat(cardsApi.middleware)
    .concat(invoicesApi.middleware)
    .concat(accountsApi.middleware)
    .concat(transactionsApi.middleware),
});
