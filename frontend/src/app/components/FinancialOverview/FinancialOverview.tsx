"use client";

import { useState, useEffect } from "react";
import { useGetAccountsQuery } from "@/redux/api/accountsApi";
import { useGetTransactionsQuery } from "@/redux/api/transactionsApi";
import * as S from "./financialOverviewStyle";
import { FiTrendingUp, FiTrendingDown, FiDollarSign, FiCreditCard } from "react-icons/fi";

interface FinancialSummary {
  totalBalance: number;
  monthlyIncome: number;
  monthlyExpenses: number;
  netWorth: number;
  savingsRate: number;
}

export default function FinancialOverview() {
  const [summary, setSummary] = useState<FinancialSummary>({
    totalBalance: 0,
    monthlyIncome: 0,
    monthlyExpenses: 0,
    netWorth: 0,
    savingsRate: 0
  });

  const { data: accounts, isLoading: accountsLoading } = useGetAccountsQuery({});
  const { data: transactions, isLoading: transactionsLoading } = useGetTransactionsQuery({});

  useEffect(() => {
    if (accounts && transactions) {
      calculateFinancialSummary();
    }
  }, [accounts, transactions]);

  const calculateFinancialSummary = () => {
    // Cálculos baseados nos dados reais
    const totalBalance = accounts?.reduce((sum: number, account: any) => sum + account.balance, 0) || 0;
    
    const currentMonth = new Date().getMonth();
    const currentYear = new Date().getFullYear();
    
    const monthlyTransactions = transactions?.filter((transaction: any) => {
      const transactionDate = new Date(transaction.timestamp);
      return transactionDate.getMonth() === currentMonth && transactionDate.getFullYear() === currentYear;
    }) || [];

    const monthlyIncome = monthlyTransactions
      .filter((t: any) => t.category?.type === 'INCOME')
      .reduce((sum: number, t: any) => sum + t.value, 0);

    const monthlyExpenses = monthlyTransactions
      .filter((t: any) => t.category?.type === 'EXPENSE')
      .reduce((sum: number, t: any) => sum + t.value, 0);

    const netWorth = totalBalance;
    const savingsRate = monthlyIncome > 0 ? ((monthlyIncome - monthlyExpenses) / monthlyIncome) * 100 : 0;

    setSummary({
      totalBalance,
      monthlyIncome,
      monthlyExpenses,
      netWorth,
      savingsRate
    });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  if (accountsLoading || transactionsLoading) {
    return <S.LoadingContainer>Carregando dados financeiros...</S.LoadingContainer>;
  }

  return (
    <S.OverviewContainer>
      <S.Title>Visão Geral Financeira</S.Title>
      
      <S.CardsGrid>
        <S.SummaryCard>
          <S.CardHeader>
            <FiDollarSign size={24} color="#4CAF50" />
            <span>Saldo Total</span>
          </S.CardHeader>
          <S.CardValue positive={summary.totalBalance >= 0}>
            {formatCurrency(summary.totalBalance)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiTrendingUp size={24} color="#2196F3" />
            <span>Receitas do Mês</span>
          </S.CardHeader>
          <S.CardValue positive={true}>
            {formatCurrency(summary.monthlyIncome)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiTrendingDown size={24} color="#FF5722" />
            <span>Despesas do Mês</span>
          </S.CardHeader>
          <S.CardValue positive={false}>
            {formatCurrency(summary.monthlyExpenses)}
          </S.CardValue>
        </S.SummaryCard>

        <S.SummaryCard>
          <S.CardHeader>
            <FiCreditCard size={24} color="#9C27B0" />
            <span>Taxa de Poupança</span>
          </S.CardHeader>
          <S.CardValue positive={summary.savingsRate >= 0}>
            {summary.savingsRate.toFixed(1)}%
          </S.CardValue>
        </S.SummaryCard>
      </S.CardsGrid>

      <S.ChartsSection>
        <S.ChartContainer>
          <S.ChartTitle>Evolução Patrimonial</S.ChartTitle>
          <S.ChartPlaceholder>
            Gráfico de evolução patrimonial será implementado aqui
          </S.ChartPlaceholder>
        </S.ChartContainer>

        <S.ChartContainer>
          <S.ChartTitle>Distribuição de Gastos</S.ChartTitle>
          <S.ChartPlaceholder>
            Gráfico de pizza com categorias de gastos será implementado aqui
          </S.ChartPlaceholder>
        </S.ChartContainer>
      </S.ChartsSection>

      <S.RecentTransactions>
        <S.SectionTitle>Transações Recentes</S.SectionTitle>
        <S.TransactionsList>
          {transactions?.slice(0, 5).map((transaction: any) => (
            <S.TransactionItem key={transaction.id}>
              <S.TransactionInfo>
                <S.TransactionDescription>
                  {transaction.category?.name || 'Sem categoria'}
                </S.TransactionDescription>
                <S.TransactionDate>
                  {new Date(transaction.timestamp).toLocaleDateString('pt-BR')}
                </S.TransactionDate>
              </S.TransactionInfo>
              <S.TransactionAmount positive={transaction.category?.type === 'INCOME'}>
                {transaction.category?.type === 'INCOME' ? '+' : '-'}
                {formatCurrency(Math.abs(transaction.value))}
              </S.TransactionAmount>
            </S.TransactionItem>
          ))}
        </S.TransactionsList>
      </S.RecentTransactions>
    </S.OverviewContainer>
  );
}