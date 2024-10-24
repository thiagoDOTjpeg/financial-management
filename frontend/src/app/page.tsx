"use client";

import { Provider } from "react-redux";
import Dashboard from "./components/Dashboard/Dashboard";
import { store } from "../redux/store/store";

export default function Home() {
  return (
    <Provider store={store}>
      <div>
        <main>
          <Dashboard />
        </main>
        <footer></footer>
      </div>
    </Provider>
  );
}
