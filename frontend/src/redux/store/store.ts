import logger from "redux-logger";
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { cardsApi } from "../api/cardsApi";
import { accountsApi } from "../api/accountsApi";
import { transactionsApi } from "../api/transactionsApi";
import { invoicesApi } from "../api/invoicesApi";
import sidebarSlice from "../slices/sidebarSlice";

export const store = configureStore({
  reducer: {
    [cardsApi.reducerPath]: cardsApi.reducer,
    [invoicesApi.reducerPath]: invoicesApi.reducer,
    [accountsApi.reducerPath]: accountsApi.reducer,
    [transactionsApi.reducerPath]: transactionsApi.reducer,
    sidebar: sidebarSlice,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware()
    .concat(logger)
    .concat(cardsApi.middleware)
    .concat(invoicesApi.middleware)
    .concat(accountsApi.middleware)
    .concat(transactionsApi.middleware),
});
