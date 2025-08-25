export type AccountStatus = 'ACTIVE' | 'INACTIVE';
export type CategoryType = 'EXPENSE' | 'INCOME';
export type PaymentType = 'CREDIT' | 'DEBIT' | 'TRANSFER';
export type InvoiceStatus = 'OPEN' | 'CLOSED' | 'PAID';

export interface UserMinimal {
  id: string;
  username: string;
}

export interface BankAccountMinimal {
  id: string;
  bankName: string;
  user: UserMinimal;
}

export interface CardMinimal {
  id: string;
  creditLimit: number;
  cardBrand: string;
  closingDay: number;
  dueDay: number;
}

export interface CategoryMinimal {
  name: string;
  type: CategoryType;
}

export interface InstallmentMinimal {
  id: string;
  numberInstallment: number;
  installmentValue: number;
}

export interface InvoiceMinimal {
  id: string;
  billingMonth: Date;
}

export interface User {
  id: string;
  username: string;
  fullName: string;
  email: string;
  lastLogin: Date;
  accountStatus: AccountStatus;
  roles: string[];
  createdAt: Date;
  createdBy: string;
  updatedAt: Date;
  updatedBy: string;
}

export interface BankAccount {
  id: string;
  bankName: string;
  balance: number;
  user: UserMinimal;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}

export interface Card {
  id: string;
  creditLimit: number;
  cardBrand: string;
  bankAccount: BankAccountMinimal;
  closingDay: number;
  dueDay: number;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}

export interface Category {
  id: string;
  name: string;
  type: CategoryType;
  user: UserMinimal;
}

export interface Transaction {
  id: string;
  timestamp: Date;
  value: number;
  paymentType: PaymentType;
  category: CategoryMinimal;
  installment?: InstallmentMinimal;
  invoice?: InvoiceMinimal;
}

export interface Invoice {
  id: string;
  billingMonth: Date;
  totalValue: number;
  status: InvoiceStatus;
  closingDate: Date;
  card: CardMinimal;
  installments: InstallmentMinimal[];
}

export interface CreateBankAccountData {
  bankName: string;
  balance: number;
}

export interface CreateCardData {
  creditLimit: number;
  cardBrand: string;
  closingDay: number;
  dueDay: number;
}

export interface CreateCategoryData {
  name: string;
  type: CategoryType;
}

export interface CreateTransactionData {
  timestamp?: string;
  value: number;
  paymentType: PaymentType;
  category: { id: string };
  numberInstallment?: number;
  installmentValue?: number;
  fromAccountId?: string;
  toAccountId?: string;
}
