import React, { useEffect, useState, type FormEvent } from "react";
import { useNavigationStore } from "@/store/navigationStore";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
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
import { PlusCircle } from "lucide-react";
import type { CreateCategoryData, Category, CategoryType } from "@/types";
import { useCategoryStore } from "@/store/categoriesStore";

const categoryTypeTranslations = new Map<CategoryType, string>([
  ["INCOME", "Receita"],
  ["EXPENSE", "Despesa"],
]);

const CategoriesPage: React.FC = () => {
  const setCurrentPageTitle = useNavigationStore(
    (state) => state.setCurrentPageTitle
  );
  const {
    categories,
    totalPages,
    currentPage,
    fetchCategories,
    addCategory,
    loading,
  } = useCategoryStore();

  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [name, setName] = useState("");
  const [type, setType] = useState<CategoryType>("EXPENSE");

  useEffect(() => {
    setCurrentPageTitle("Categorias");
    fetchCategories(0);
  }, [setCurrentPageTitle]);

  const handleAddCategory = async (e: FormEvent) => {
    e.preventDefault();
    const newCategoryData: CreateCategoryData = { name, type };
    await addCategory(newCategoryData);

    setName("");
    setType("EXPENSE");
    setIsDialogOpen(false);
  };

  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchCategories(page);
    }
  };

  return (
    <div className="flex flex-col gap-8">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle>Suas Categorias</CardTitle>
            <CardDescription>
              Organize suas receitas e despesas.
            </CardDescription>
          </div>
          <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
            <DialogTrigger asChild>
              <Button>
                <PlusCircle className="mr-2 h-4 w-4" />
                Adicionar Categoria
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>Nova Categoria</DialogTitle>
              </DialogHeader>
              <form onSubmit={handleAddCategory}>
                <div className="grid gap-4 py-4">
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="name" className="text-right">
                      Nome
                    </Label>
                    <Input
                      id="name"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      className="col-span-3"
                      placeholder="Ex: Supermercado, Salário"
                      required
                    />
                  </div>
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label className="text-right">Tipo</Label>
                    <RadioGroup
                      defaultValue="EXPENSE"
                      value={type}
                      onValueChange={(value: CategoryType) => setType(value)}
                      className="col-span-3 flex gap-4"
                    >
                      <div className="flex items-center space-x-2">
                        <RadioGroupItem value="EXPENSE" id="r-expense" />
                        <Label htmlFor="r-expense">Despesa</Label>
                      </div>
                      <div className="flex items-center space-x-2">
                        <RadioGroupItem value="INCOME" id="r-income" />
                        <Label htmlFor="r-income">Receita</Label>
                      </div>
                    </RadioGroup>
                  </div>
                </div>
                <DialogFooter>
                  <Button type="submit">Salvar</Button>
                </DialogFooter>
              </form>
            </DialogContent>
          </Dialog>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center py-10">A carregar categorias...</p>
          ) : (
            <>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Nome da Categoria</TableHead>
                    <TableHead>Tipo</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {categories.map((category: Category) => (
                    <TableRow key={category.id}>
                      <TableCell className="font-medium">
                        {category.name}
                      </TableCell>
                      <TableCell>
                        <Badge
                          variant={
                            category.type === "INCOME"
                              ? "default"
                              : "destructive"
                          }
                        >
                          {categoryTypeTranslations.get(category.type) ||
                            category.type}
                        </Badge>
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

export default CategoriesPage;
