import Transactions from "../controllers/transacoesController";
import Accounts from "../controllers/accountsController";
import { Router } from "express"

const router = Router();

router.get("/transactions", (req, res) => Transactions.getAllTransaction(req, res));

router.get("/transactions/:id", (req, res) => Transactions.getTransactionById(req, res));

router.put("/transactions/:id", (req, res) => Transactions.updateTransaction(req, res));

router.delete("/transactions/:id", (req, res) => Transactions.deleteTransaction(req, res));

router.post("/transactions", (req, res) => Transactions.createTransaction(req, res));

router.get("/accounts", (req, res) => Accounts.getAllAccounts(req, res));



export default router;
