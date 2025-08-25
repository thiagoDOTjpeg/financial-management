import React, { useEffect, useState, type FormEvent } from "react";
import { useNavigationStore } from "@/store/navigationStore";
import { useBankAccountStore } from "@/store/bankAccountStore";
import { useCardStore } from "@/store/cardStore"; // Importe o novo store de cartões

// Imports de componentes shadcn/ui
import { Button } from "@/components/ui/button";
import {
  Card as UICard,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
  SheetFooter,
  SheetClose,
} from "@/components/ui/sheet";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { PlusCircle, CreditCard, ChevronsUpDown } from "lucide-react";
import type {
  CreateBankAccountData,
  CreateCardData,
  BankAccount as BankAccountType,
  Card as CardType,
  CardMinimal,
} from "@/types";
import { useBrazilBanksStore } from "@/store/brazilBankStore";

const formatCurrency = (value: number) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(
    value
  );

// --- Componente para o formulário de Adicionar Cartão ---
const AddCardForm = ({
  accountId,
  onCardAdded,
}: {
  accountId: string;
  onCardAdded: () => void;
}) => {
  const { addCardToAccount } = useCardStore();
  const [cardBrand, setCardBrand] = useState("");
  const [creditLimit, setCreditLimit] = useState<number | string>("");
  const [closingDay, setClosingDay] = useState<number | string>("");
  const [dueDay, setDueDay] = useState<number | string>("");

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const cardData: CreateCardData = {
      cardBrand,
      creditLimit: Number(creditLimit),
      closingDay: Number(closingDay),
      dueDay: Number(dueDay),
    };
    await addCardToAccount(accountId, cardData);
    onCardAdded(); // Fecha o painel
  };

  return (
    <form onSubmit={handleSubmit} className="grid gap-4 py-4">
      <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="cardBrand" className="text-right">
          Bandeira
        </Label>
        <Input
          id="cardBrand"
          value={cardBrand}
          onChange={(e) => setCardBrand(e.target.value)}
          className="col-span-3"
          placeholder="Ex: Visa, Mastercard"
          required
        />
      </div>
      <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="creditLimit" className="text-right">
          Limite
        </Label>
        <Input
          id="creditLimit"
          type="number"
          value={creditLimit}
          onChange={(e) => setCreditLimit(e.target.value)}
          className="col-span-3"
          placeholder="R$ 5000,00"
          required
        />
      </div>
      <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="closingDay" className="text-right">
          Dia do Fechamento
        </Label>
        <Input
          id="closingDay"
          type="number"
          value={closingDay}
          onChange={(e) => setClosingDay(e.target.value)}
          className="col-span-3"
          placeholder="Ex: 25"
          required
        />
      </div>
      <div className="grid grid-cols-4 items-center gap-4">
        <Label htmlFor="dueDay" className="text-right">
          Dia do Vencimento
        </Label>
        <Input
          id="dueDay"
          type="number"
          value={dueDay}
          onChange={(e) => setDueDay(e.target.value)}
          className="col-span-3"
          placeholder="Ex: 5"
          required
        />
      </div>
      <SheetFooter className="mt-4">
        <SheetClose asChild>
          <Button type="button" variant="outline">
            Cancelar
          </Button>
        </SheetClose>
        <Button type="submit">Adicionar Cartão</Button>
      </SheetFooter>
    </form>
  );
};

const BankAccountsPage: React.FC = () => {
  const setCurrentPageTitle = useNavigationStore(
    (state) => state.setCurrentPageTitle
  );
  const { bankAccounts, getBankAccounts, createBankAccount, loading } =
    useBankAccountStore();
  const { cardsByAccount, fetchCardsByAccountId } = useCardStore();
  const { banks, getBanks } = useBrazilBanksStore();

  const [isAddAccountDialogOpen, setIsAddAccountDialogOpen] = useState(false);
  const [bankName, setBankName] = useState<string>("");
  const [initialBalance, setInitialBalance] = useState<number | string>("");
  const [isComboboxOpen, setIsComboboxOpen] = useState(false);

  useEffect(() => {
    setCurrentPageTitle("Contas e Cartões");
    getBankAccounts();
    getBanks();
  }, [setCurrentPageTitle, getBankAccounts, getBanks]);

  const handleAddAccount = async (e: FormEvent) => {
    e.preventDefault();
    if (!bankName) return;
    const newAccountData: CreateBankAccountData = {
      bankName,
      balance: Number(initialBalance),
    };
    await createBankAccount(newAccountData);
    setBankName("");
    setInitialBalance("");
    setIsAddAccountDialogOpen(false);
  };

  return (
    <div className="flex flex-col gap-8">
      <UICard>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle>Suas Contas e Cartões</CardTitle>
          <Dialog
            open={isAddAccountDialogOpen}
            onOpenChange={setIsAddAccountDialogOpen}
          >
            <DialogTrigger asChild>
              <Button>
                <PlusCircle className="mr-2 h-4 w-4" />
                Adicionar Conta
              </Button>
            </DialogTrigger>
            <DialogContent
              className="sm:max-w-[425px]"
              onInteractOutside={(e) => e.preventDefault()}
            >
              <DialogHeader>
                <DialogTitle>Nova Conta Bancária</DialogTitle>
              </DialogHeader>
              <form onSubmit={handleAddAccount} className="space-y-4 pt-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label className="text-right">Banco</Label>
                  <Popover
                    open={isComboboxOpen}
                    onOpenChange={setIsComboboxOpen}
                  >
                    <PopoverTrigger asChild>
                      <Button
                        variant="outline"
                        role="combobox"
                        aria-expanded={isComboboxOpen}
                        className="col-span-3 justify-between font-normal"
                      >
                        {bankName
                          ? banks.find((b) => b.name === bankName)?.name
                          : "Selecione um banco..."}
                        <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-[300px] p-0">
                      <Command>
                        <CommandInput placeholder="Pesquisar banco..." />
                        <CommandList>
                          <CommandEmpty>Nenhum banco encontrado.</CommandEmpty>
                          <CommandGroup>
                            {banks.map((b) => (
                              <CommandItem
                                key={b.ispb}
                                value={b.name}
                                onSelect={(val) => {
                                  setBankName(val);
                                  setIsComboboxOpen(false);
                                }}
                              >
                                {b.name}
                              </CommandItem>
                            ))}
                          </CommandGroup>
                        </CommandList>
                      </Command>
                    </PopoverContent>
                  </Popover>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="balance" className="text-right">
                    Saldo Inicial
                  </Label>
                  <Input
                    id="balance"
                    type="number"
                    value={initialBalance}
                    onChange={(e) => setInitialBalance(e.target.value)}
                    className="col-span-3"
                    required
                  />
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
            <p>A carregar contas...</p>
          ) : (
            <Accordion type="single" collapsible className="w-full">
              {bankAccounts.map((account: BankAccountType) => (
                <AccordionItem value={account.id} key={account.id}>
                  <AccordionTrigger
                    onClick={() => fetchCardsByAccountId(account.id)}
                    className="px-4 hover:no-underline"
                  >
                    <div className="flex justify-between w-full pr-4">
                      <span className="font-medium text-lg">
                        {account.bankName}
                      </span>
                      <span className="text-muted-foreground">
                        {formatCurrency(account.balance)}
                      </span>
                    </div>
                  </AccordionTrigger>
                  <AccordionContent className="p-4 bg-muted/50 border-t">
                    <div className="flex justify-between items-center mb-4">
                      <h4 className="font-semibold">Cartões de Crédito</h4>
                      <Sheet>
                        <SheetTrigger asChild>
                          <Button variant="outline" size="sm">
                            <PlusCircle className="mr-2 h-4 w-4" />
                            Gerir Cartões
                          </Button>
                        </SheetTrigger>
                        <SheetContent>
                          <SheetHeader>
                            <SheetTitle>
                              Cartões de {account.bankName}
                            </SheetTitle>
                          </SheetHeader>
                          <Separator className="my-4" />
                          <div className="space-y-4">
                            {cardsByAccount[account.id]?.map(
                              (card: CardMinimal) => (
                                <div
                                  key={card.id}
                                  className="flex items-center justify-between rounded-md border p-3"
                                >
                                  <div className="flex items-center gap-3">
                                    <CreditCard className="h-5 w-5 text-muted-foreground" />
                                    <div>
                                      <p className="font-semibold">
                                        {card.cardBrand}
                                      </p>
                                      <p className="text-sm text-muted-foreground">
                                        Limite:{" "}
                                        {formatCurrency(card.creditLimit)}
                                      </p>
                                    </div>
                                  </div>
                                </div>
                              )
                            )}
                            {(!cardsByAccount[account.id] ||
                              cardsByAccount[account.id].length === 0) && (
                              <p className="text-sm text-center text-muted-foreground">
                                Nenhum cartão adicionado.
                              </p>
                            )}
                          </div>
                          <Separator className="my-4" />
                          <h5 className="text-lg font-semibold mb-2">
                            Adicionar Novo Cartão
                          </h5>
                          <AddCardForm
                            accountId={account.id}
                            onCardAdded={() => {
                              /* Lógica para fechar o Sheet se necessário */
                            }}
                          />
                        </SheetContent>
                      </Sheet>
                    </div>
                    <div className="space-y-2">
                      {
                        // 1. Estado de Carregamento: Mostra enquanto a API busca os dados.
                        loading && !cardsByAccount[account.id] ? (
                          <p className="text-sm text-center text-muted-foreground py-2">
                            A carregar cartões...
                          </p>
                        ) : // 2. Estado Vazio: Mostra se a busca terminou e não retornou cartões.
                        cardsByAccount[account.id]?.length === 0 ? (
                          <p className="text-sm text-center text-muted-foreground py-2">
                            Nenhum cartão de crédito associado.
                          </p>
                        ) : (
                          // 3. Estado com Dados: Mapeia e exibe os cartões.
                          cardsByAccount[account.id]?.map(
                            (card: CardMinimal) => (
                              <div
                                key={card.id}
                                className="flex items-center justify-between text-sm p-2 rounded-md hover:bg-background"
                              >
                                <span>{card.cardBrand}</span>
                                <span className="text-muted-foreground">
                                  Limite: {formatCurrency(card.creditLimit)}
                                </span>
                              </div>
                            )
                          )
                        )
                      }
                    </div>
                  </AccordionContent>
                </AccordionItem>
              ))}
            </Accordion>
          )}
        </CardContent>
      </UICard>
    </div>
  );
};

export default BankAccountsPage;
