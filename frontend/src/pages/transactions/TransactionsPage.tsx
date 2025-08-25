import React, { useEffect } from "react";
import { useNavigationStore } from "@/store/navigationStore";
import { useTransactionsStore } from "@/store/transactionStore";

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
import { Badge } from "@/components/ui/badge";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationPrevious,
  PaginationLink,
  PaginationNext,
} from "@/components/ui/pagination";
import { ArrowUpRight, ArrowDownRight, ArrowRightLeft } from "lucide-react";
import type { Transaction } from "@/types";

const formatCurrency = (value: number) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    value
  );

const paymentTypeDetails = new Map([
  ["CREDIT", { icon: ArrowDownRight, text: "Crédito", color: "text-red-600" }],
  ["DEBIT", { icon: ArrowDownRight, text: "Débito", color: "text-red-600" }],
  [
    "INCOME",
    { icon: ArrowUpRight, text: "Receita", color: "text-emerald-600" },
  ],
  [
    "TRANSFER",
    { icon: ArrowRightLeft, text: "Transferência", color: "text-blue-600" },
  ],
]);

const TransactionsPage: React.FC = () => {
  const setCurrentPageTitle = useNavigationStore(
    (state) => state.setCurrentPageTitle
  );
  const { transactions, totalPages, currentPage, fetchTransactions, loading } =
    useTransactionsStore();

  useEffect(() => {
    setCurrentPageTitle("Transações");
    fetchTransactions(0);
  }, [setCurrentPageTitle]);

  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchTransactions(page);
    }
  };

  return (
    <div className="flex flex-col gap-8">
      <Card>
        <CardHeader>
          <CardTitle>Histórico de Transações</CardTitle>
          <CardDescription>
            Visualize todas as suas transações registadas.
          </CardDescription>
        </CardHeader>
        <CardContent>
          {loading && (
            <p className="text-center py-10">A carregar transações...</p>
          )}
          {!loading && (
            <>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Descrição</TableHead>
                    <TableHead>Data</TableHead>
                    <TableHead>Tipo</TableHead>
                    <TableHead className="text-right">Valor</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {transactions.map((transaction: Transaction) => {
                    const details = paymentTypeDetails.get(
                      transaction.paymentType
                    ) || {
                      icon: ArrowDownRight,
                      text: transaction.paymentType,
                      color: "text-foreground",
                    };
                    return (
                      <TableRow key={transaction.id}>
                        <TableCell className="font-medium">
                          {transaction.category?.name || "Sem categoria"}
                        </TableCell>
                        <TableCell className="text-muted-foreground">
                          {new Date(transaction.timestamp).toLocaleDateString(
                            "pt-BR"
                          )}
                        </TableCell>
                        <TableCell>
                          <Badge
                            variant="outline"
                            className="flex items-center gap-1 w-fit"
                          >
                            <details.icon
                              className={`h-3 w-3 ${details.color}`}
                            />
                            {details.text}
                          </Badge>
                        </TableCell>
                        <TableCell
                          className={`text-right font-semibold ${details.color}`}
                        >
                          {formatCurrency(transaction.value)}
                        </TableCell>
                      </TableRow>
                    );
                  })}
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

export default TransactionsPage;
