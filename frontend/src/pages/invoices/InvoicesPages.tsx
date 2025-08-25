// src/pages/invoices/InvoicesPage.tsx

import React, { useEffect, useMemo, useState } from "react";
import { useNavigationStore } from "@/store/navigationStore";
import { useInvoiceStore } from "@/store/invoiceStore";
import { useBankAccountStore } from "@/store/bankAccountStore";
import { useCardStore } from "@/store/cardStore";

// Imports de UI
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
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationPrevious,
  PaginationLink,
  PaginationNext,
} from "@/components/ui/pagination";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";
import type { Invoice } from "@/types";

const formatCurrency = (value: number) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    value
  );

const InvoicesPage: React.FC = () => {
  const setCurrentPageTitle = useNavigationStore(
    (state) => state.setCurrentPageTitle
  );
  const {
    invoices,
    allInvoices,
    totalPages,
    currentPage,
    fetchInvoices,
    fetchAllInvoicesForChart,
    loading,
  } = useInvoiceStore();
  const { bankAccounts, getBankAccounts } = useBankAccountStore();
  const { cardsByAccount, fetchCardsByAccountId } = useCardStore();

  const [selectedMonth, setSelectedMonth] = useState<string>(() => {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth()).padStart(2, "0")}`;
  });

  useEffect(() => {
    setCurrentPageTitle("Faturas");
    fetchInvoices(0);
    getBankAccounts();
    fetchAllInvoicesForChart();
  }, [
    setCurrentPageTitle,
    fetchInvoices,
    getBankAccounts,
    fetchAllInvoicesForChart,
  ]);

  useEffect(() => {
    if (bankAccounts.length > 0) {
      bankAccounts.forEach((account) => fetchCardsByAccountId(account.id));
    }
  }, [bankAccounts, fetchCardsByAccountId]);

  const chartData = useMemo(() => {
    const [selectedYear, selectedMonthIndex] = selectedMonth
      .split("-")
      .map(Number);

    const monthlyInvoices = allInvoices.filter((inv) => {
      const invoiceBillingDate = new Date(inv.billingMonth);
      return (
        invoiceBillingDate.getUTCMonth() === selectedMonthIndex &&
        invoiceBillingDate.getUTCFullYear() === selectedYear
      );
    });

    const cardToBankMap = new Map<string, string>();
    bankAccounts.forEach((account) => {
      (cardsByAccount[account.id] || []).forEach((card) =>
        cardToBankMap.set(card.id, account.bankName)
      );
    });

    const invoicesByBank: Record<string, number> = {};
    monthlyInvoices.forEach((invoice) => {
      const bankName = cardToBankMap.get(invoice.card.id);
      if (bankName) {
        invoicesByBank[bankName] =
          (invoicesByBank[bankName] || 0) + invoice.totalValue;
      }
    });

    return Object.entries(invoicesByBank).map(([name, Fatura]) => ({
      name,
      Fatura,
    }));
  }, [selectedMonth, allInvoices, bankAccounts, cardsByAccount]);

  const availableMonths = useMemo(() => {
    const availableMonthsSet = new Set<string>();
    allInvoices.forEach((item) => {
      const date = new Date(item.billingMonth);
      const year = date.getUTCFullYear();
      const month = String(date.getUTCMonth()).padStart(2, "0");
      availableMonthsSet.add(`${year}-${month}`);
    });
    return Array.from(availableMonthsSet).sort().reverse();
  }, [allInvoices]);

  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) fetchInvoices(page);
  };

  return (
    <div className="flex flex-col gap-8">
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <div>
              <CardTitle>Faturas por Banco</CardTitle>
              <CardDescription>
                Valor total das faturas para o mês selecionado.
              </CardDescription>
            </div>
            <div className="flex items-center gap-2">
              <Select value={selectedMonth} onValueChange={setSelectedMonth}>
                <SelectTrigger className="w-[180px]">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  {availableMonths.map((monthStr) => {
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
            </div>
          </div>
        </CardHeader>
        <CardContent className="h-[300px] w-full pl-2">
          <ChartContainer
            config={{
              Fatura: { label: "Fatura", color: "hsl(var(--primary))" },
            }}
            className="h-full w-full"
          >
            <ResponsiveContainer>
              <BarChart data={chartData} layout="vertical">
                <CartesianGrid horizontal={false} />
                <XAxis
                  type="number"
                  tickFormatter={(value) => formatCurrency(value as number)}
                  fontSize={12}
                />
                <YAxis type="category" dataKey="name" fontSize={12} />
                <ChartTooltip
                  cursor={false}
                  content={
                    <ChartTooltipContent
                      indicator="dot"
                      formatter={(value) => formatCurrency(value as number)}
                    />
                  }
                />
                <Bar dataKey="Fatura" fill="var(--color-primary)" radius={4} />
              </BarChart>
            </ResponsiveContainer>
          </ChartContainer>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Histórico de Faturas</CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center py-10">A carregar faturas...</p>
          ) : (
            <>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Cartão</TableHead>
                    <TableHead>Mês de Faturamento</TableHead>
                    <TableHead className="text-right">Valor</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {invoices.map((invoice: Invoice) => (
                    <TableRow key={invoice.id}>
                      <TableCell className="font-medium">
                        {invoice.card.cardBrand}
                      </TableCell>
                      <TableCell className="text-muted-foreground">
                        {new Date(invoice.billingMonth).toLocaleString(
                          "pt-BR",
                          { month: "long", year: "numeric" }
                        )}
                      </TableCell>
                      <TableCell className="text-right font-semibold">
                        {formatCurrency(invoice.totalValue)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
              <Pagination className="mt-6">
                <PaginationContent>
                  <PaginationItem>
                    <PaginationPrevious
                      href="#"
                      onClick={(e) => {
                        e.preventDefault();
                        handlePageChange(currentPage - 1);
                      }}
                      className={
                        currentPage === 0
                          ? "pointer-events-none opacity-50"
                          : ""
                      }
                    />
                  </PaginationItem>
                  {[...Array(totalPages).keys()].map((pageNumber) => (
                    <PaginationItem key={pageNumber}>
                      <PaginationLink
                        href="#"
                        onClick={(e) => {
                          e.preventDefault();
                          handlePageChange(pageNumber);
                        }}
                        isActive={currentPage === pageNumber}
                      >
                        {pageNumber + 1}
                      </PaginationLink>
                    </PaginationItem>
                  ))}
                  <PaginationItem>
                    <PaginationNext
                      href="#"
                      onClick={(e) => {
                        e.preventDefault();
                        handlePageChange(currentPage + 1);
                      }}
                      className={
                        currentPage >= totalPages - 1
                          ? "pointer-events-none opacity-50"
                          : ""
                      }
                    />
                  </PaginationItem>
                </PaginationContent>
              </Pagination>
            </>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default InvoicesPage;
