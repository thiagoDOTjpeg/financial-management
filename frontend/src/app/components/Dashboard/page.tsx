"use client";

import { useAddAccountMutation } from "@/redux/api/accountsApi";
import { useState } from "react";
import styles from "./styles.module.scss";

export default function Dashboard() {
  const [accountData, setAccountData] = useState({ banco: "", saldo: "" });
  const [createAccount, { isLoading, error }] = useAddAccountMutation();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setAccountData({ ...accountData, [name]: value });
  };

  const handleSubmitBank = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await createAccount(accountData).unwrap();
      setAccountData({ banco: "", saldo: "" });
    } catch (error) {}
  };

  return (
    <div>
      <main className={styles.wrapperDashboard}>
        <form onSubmit={handleSubmitBank}>
          <h1>Adicionar Banco</h1>
          <span>
            <label>Banco</label>
            <input
              type="text"
              name="banco"
              value={accountData.banco}
              onChange={handleChange}
              required
            />
          </span>
          <br />
          <span>
            <label>Saldo</label>
            <input
              type="text"
              name="saldo"
              value={accountData.saldo}
              onChange={handleChange}
              required
            />
          </span>
          <br />
          <button type="submit" disabled={isLoading}>
            {isLoading ? "Carregando..." : "Adicionar Banco"}
          </button>
        </form>
      </main>
      <footer></footer>
    </div>
  );
}
