// src/pages/dashboard/DashboardPage.tsx

import React, { useEffect, useMemo, useState } from "react";
import { useNavigationStore } from "@/store/navigationStore";
import { useBankAccountStore } from "@/store/bankAccountStore";
import { useTransactionsStore } from "@/store/transactionStore";
import { useInvoiceStore } from "@/store/invoiceStore";
import { useCardStore } from "@/store/cardStore";

// Imports dos componentes shadcn/ui
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";
import {
  BarChart,
  Bar,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";
import {
  TrendingDown,
  Wallet,
  FileText,
  CalendarDays,
  ArrowLeft,
  ArrowRight,
} from "lucide-react";

const formatCurrency = (value: number) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    value
  );

const DashboardPage: React.FC = () => {
  const setCurrentPageTitle = useNavigationStore(
    (state) => state.setCurrentPageTitle
  );
  const {
    bankAccounts,
    getBankAccounts,
    loading: bankAccountsLoading,
  } = useBankAccountStore();
  const {
    transactions,
    fetchTransactions,
    loading: transactionsLoading,
  } = useTransactionsStore();
  const {
    invoices,
    fetchInvoices,
    loading: invoicesLoading,
  } = useInvoiceStore();
  const { cardsByAccount, fetchCardsByAccountId } = useCardStore();

  const [selectedMonth, setSelectedMonth] = useState<string>(() => {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth()).padStart(2, "0")}`;
  });

  useEffect(() => {
    setCurrentPageTitle("Dashboard");
    const loadInitialData = async () => {
      await getBankAccounts();
      await Promise.all([fetchTransactions(), fetchInvoices()]);
    };
    loadInitialData();
  }, [setCurrentPageTitle, getBankAccounts, fetchTransactions, fetchInvoices]);

  useEffect(() => {
    if (bankAccounts.length > 0) {
      bankAccounts.forEach((account) => {
        fetchCardsByAccountId(account.id);
      });
    }
  }, [bankAccounts, fetchCardsByAccountId]);

  const dashboardData = useMemo(() => {
    // Lógica para gerar meses disponíveis
    const availableMonthsSet = new Set<string>();
    [...transactions, ...invoices].forEach((item) => {
      const date = new Date(
        "timestamp" in item ? item.timestamp : item.billingMonth
      );
      const year = date.getUTCFullYear();
      const month = String(date.getUTCMonth()).padStart(2, "0");
      availableMonthsSet.add(`${year}-${month}`);
    });
    const availableMonths = Array.from(availableMonthsSet).sort().reverse();

    const [selectedYear, selectedMonthIndex] = selectedMonth
      .split("-")
      .map(Number);

    // Filtros por mês
    const monthlyTransactions = transactions.filter((t) => {
      const transactionDate = new Date(t.timestamp);
      return (
        transactionDate.getMonth() === selectedMonthIndex &&
        transactionDate.getFullYear() === selectedYear
      );
    });
    const monthlyInvoices = invoices.filter((inv) => {
      const invoiceBillingDate = new Date(inv.billingMonth);
      return (
        invoiceBillingDate.getUTCMonth() === selectedMonthIndex &&
        invoiceBillingDate.getUTCFullYear() === selectedYear
      );
    });

    // Lógica de agrupamento de faturas por banco
    const cardToBankMap = new Map<string, string>();
    bankAccounts.forEach((account) => {
      (cardsByAccount[account.id] || []).forEach((card) => {
        cardToBankMap.set(card.id, account.bankName);
      });
    });
    const monthlyInvoicesByBank: Record<string, number> = {};
    monthlyInvoices.forEach((invoice) => {
      const bankName = cardToBankMap.get(invoice.card.id);
      if (bankName) {
        monthlyInvoicesByBank[bankName] =
          (monthlyInvoicesByBank[bankName] || 0) + invoice.totalValue;
      }
    });
    const monthlyInvoicesList = Object.entries(monthlyInvoicesByBank).map(
      ([bankName, totalValue]) => ({
        bankName,
        totalValue,
      })
    );

    // Cálculos de cards
    const totalBalance = bankAccounts.reduce(
      (sum, account) => sum + (account.balance || 0),
      0
    );
    const monthlyExpenses = monthlyTransactions.reduce(
      (sum, t) => sum + Math.abs(t.value),
      0
    );
    const monthlyInvoiceValue = monthlyInvoices.reduce(
      (sum, inv) => sum + inv.totalValue,
      0
    );
    const monthlyInstallmentValue = monthlyTransactions
      .filter(
        (t) =>
          t.paymentType === "CREDIT" &&
          t.installment &&
          t.installment.numberInstallment > 1
      )
      .reduce((sum, t) => sum + t.installment!.installmentValue, 0);

    // Gráfico de Gastos por Mês
    const expenseByMonth: { [key: string]: number } = {};
    transactions.forEach((t) => {
      const date = new Date(t.timestamp);
      const monthYear = date.toLocaleString("pt-BR", {
        month: "short",
        year: "numeric",
      });
      expenseByMonth[monthYear] = (expenseByMonth[monthYear] || 0) + t.value;
    });
    const expenseChartData = Object.keys(expenseByMonth)
      .map((key) => ({ name: key, Gastos: expenseByMonth[key] }))
      .slice(-6);

    // Gráfico de Gastos Diários
    const daysInMonth = new Date(
      selectedYear,
      selectedMonthIndex + 1,
      0
    ).getDate();
    const dailyExpensesData = Array.from({ length: daysInMonth }, (_, i) => ({
      name: `${String(i + 1).padStart(2, "0")}`,
      Gastos: 0,
    }));
    monthlyTransactions.forEach((t) => {
      const day = new Date(t.timestamp).getUTCDate();
      if (dailyExpensesData[day - 1]) {
        dailyExpensesData[day - 1].Gastos += t.value;
      }
    });

    const recentTransactions = [...monthlyTransactions]
      .sort(
        (a, b) =>
          new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime()
      )
      .slice(0, 5);

    return {
      availableMonths,
      totalBalance,
      monthlyExpenses,
      monthlyInvoiceValue,
      monthlyInvoicesByBank: monthlyInvoicesList,
      monthlyInstallmentValue,
      expenseChartData,
      dailyExpensesData,
      recentTransactions,
    };
  }, [bankAccounts, transactions, invoices, selectedMonth, cardsByAccount]);

  const handleMonthChange = (direction: "next" | "prev") => {
    const currentIndex = dashboardData.availableMonths.indexOf(selectedMonth);
    if (direction === "prev" && currentIndex > 0) {
      setSelectedMonth(dashboardData.availableMonths[currentIndex - 1]);
    } else if (
      direction === "next" &&
      currentIndex < dashboardData.availableMonths.length - 1
    ) {
      setSelectedMonth(dashboardData.availableMonths[currentIndex + 1]);
    }
  };

  const isLoading =
    bankAccountsLoading || transactionsLoading || invoicesLoading;

  if (isLoading) return <div className="p-4">Carregando...</div>;

  return (
    <div className="flex flex-col gap-8">
      {/* Seletor de Mês */}
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-bold">Resumo Mensal</h2>
        <div className="flex items-center gap-2">
          <button
            onClick={() => handleMonthChange("next")}
            disabled={
              dashboardData.availableMonths.indexOf(selectedMonth) ===
              dashboardData.availableMonths.length - 1
            }
            className="p-1 rounded-md hover:bg-accent disabled:opacity-50"
          >
            <ArrowLeft className="h-4 w-4" />
          </button>
          <Select value={selectedMonth} onValueChange={setSelectedMonth}>
            <SelectTrigger className="w-[180px]">
              <SelectValue placeholder="Selecione o mês" />
            </SelectTrigger>
            <SelectContent>
              {dashboardData.availableMonths.map((monthStr) => {
                const [year, monthIndex] = monthStr.split("-");
                const date = new Date(Number(year), Number(monthIndex));
                const label = date.toLocaleString("pt-BR", {
                  month: "long",
                  year: "numeric",
                });
                return (
                  <SelectItem key={monthStr} value={monthStr}>
                    {label.charAt(0).toUpperCase() + label.slice(1)}
                  </SelectItem>
                );
              })}
            </SelectContent>
          </Select>
          <button
            onClick={() => handleMonthChange("prev")}
            disabled={
              dashboardData.availableMonths.indexOf(selectedMonth) === 0
            }
            className="p-1 rounded-md hover:bg-accent disabled:opacity-50"
          >
            <ArrowRight className="h-4 w-4" />
          </button>
        </div>
      </div>

      {/* Cards de Resumo */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Saldo Total</CardTitle>
            <Wallet className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {formatCurrency(dashboardData.totalBalance)}
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Despesas (Mês)
            </CardTitle>
            <TrendingDown className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-red-600">
              {formatCurrency(dashboardData.monthlyExpenses)}
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Faturas do Mês
            </CardTitle>
            <FileText className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {formatCurrency(dashboardData.monthlyInvoiceValue)}
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Parcelamentos no Mês
            </CardTitle>
            <CalendarDays className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {formatCurrency(dashboardData.monthlyInstallmentValue)}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Grid de Gráficos e Faturas */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <Card className="lg:col-span-2">
          <CardHeader>
            <CardTitle>Gastos por Mês</CardTitle>
          </CardHeader>
          <CardContent className="h-[350px] w-full pl-2 min-h-[200px]">
            <ChartContainer
              config={{
                Gastos: { label: "Gastos", color: "hsl(var(--destructive))" },
              }}
              className="h-full w-full"
            >
              <ResponsiveContainer>
                <BarChart data={dashboardData.expenseChartData}>
                  <CartesianGrid vertical={false} />
                  <XAxis
                    dataKey="name"
                    tickLine={false}
                    axisLine={false}
                    tickMargin={8}
                    fontSize={12}
                  />
                  <YAxis
                    tickLine={false}
                    axisLine={false}
                    tickMargin={8}
                    fontSize={12}
                    tickFormatter={(value) => formatCurrency(value as number)}
                  />
                  <ChartTooltip
                    cursor={false}
                    content={
                      <ChartTooltipContent
                        indicator="dot"
                        formatter={(value) => formatCurrency(value as number)}
                      />
                    }
                  />
                  <Bar
                    dataKey="Gastos"
                    fill="var(--color-destructive)"
                    radius={[4, 4, 0, 0]}
                  />
                </BarChart>
              </ResponsiveContainer>
            </ChartContainer>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Faturas por Banco</CardTitle>
          </CardHeader>
          <CardContent>
            {dashboardData.monthlyInvoicesByBank.length > 0 ? (
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Banco</TableHead>
                    <TableHead className="text-right">Valor Total</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {dashboardData.monthlyInvoicesByBank.map((invoiceGroup) => (
                    <TableRow key={invoiceGroup.bankName}>
                      <TableCell className="font-medium">
                        {invoiceGroup.bankName}
                      </TableCell>
                      <TableCell className="text-right font-medium">
                        {formatCurrency(invoiceGroup.totalValue)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            ) : (
              <p className="text-sm text-muted-foreground text-center pt-8">
                Nenhuma fatura para este mês.
              </p>
            )}
          </CardContent>
        </Card>
      </div>

      {/* Gráfico de Gastos Diários */}
      <Card>
        <CardHeader>
          <CardTitle>Gastos Diários</CardTitle>
          <CardDescription>Fluxo de gastos do mês selecionado.</CardDescription>
        </CardHeader>
        <CardContent className="h-[350px] w-full pl-2 min-h-[200px]">
          <ChartContainer
            config={{
              Gastos: { label: "Gastos", color: "hsl(var(--destructive))" },
            }}
            className="h-full w-full"
          >
            <ResponsiveContainer>
              <LineChart data={dashboardData.dailyExpensesData}>
                <CartesianGrid vertical={false} />
                <XAxis
                  dataKey="name"
                  tickLine={false}
                  axisLine={false}
                  tickMargin={8}
                  fontSize={12}
                />
                <YAxis
                  tickLine={false}
                  axisLine={false}
                  tickMargin={8}
                  fontSize={12}
                  tickFormatter={(value) => formatCurrency(value as number)}
                />
                <ChartTooltip
                  cursor={false}
                  content={
                    <ChartTooltipContent
                      indicator="dot"
                      formatter={(value) => formatCurrency(value as number)}
                    />
                  }
                />
                <Line
                  dataKey="Gastos"
                  type="monotone"
                  stroke="var(--color-destructive)"
                  strokeWidth={2}
                  dot={false}
                />
              </LineChart>
            </ResponsiveContainer>
          </ChartContainer>
        </CardContent>
      </Card>

      {/* Grid de Tabelas de Transações e Contas */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <Card className="lg:col-span-2">
          <CardHeader>
            <CardTitle>Transações Recentes</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Descrição</TableHead>
                  <TableHead className="text-right">Valor</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {dashboardData.recentTransactions.map((transaction) => (
                  <TableRow key={transaction.id}>
                    <TableCell>
                      <div className="font-medium">
                        {transaction.category?.name || "Gasto Geral"}
                      </div>
                      <div className="text-sm text-muted-foreground">
                        {new Date(transaction.timestamp).toLocaleDateString(
                          "pt-BR"
                        )}
                      </div>
                    </TableCell>
                    <TableCell className="text-right font-medium text-red-600">
                      -{formatCurrency(transaction.value)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Visão Geral das Contas</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Conta</TableHead>
                  <TableHead className="text-right">Saldo</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {bankAccounts.map((account) => (
                  <TableRow key={account.id}>
                    <TableCell>
                      <div className="font-medium">{account.bankName}</div>
                    </TableCell>
                    <TableCell className="text-right font-medium">
                      {formatCurrency(account.balance)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default DashboardPage;
