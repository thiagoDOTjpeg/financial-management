"use client";

import { FinanceProvider } from './contexts/FinanceContext';
import FinanceApp from './components/FinanceApp';

export default function Home() {
  return (
    <FinanceProvider>
      <FinanceApp />
    </FinanceProvider>
  );
}