import Transactions from "../controllers/TransactionController";
import Accounts from "../controllers/accountsController";
import Card from "../controllers/cardController";
import { Router } from "express"

const router = Router();

// Transactions routes
router.get("/transactions", (req, res) => Transactions.getAllTransaction(req, res));

router.get("/transactions/:id", (req, res) => Transactions.getTransactionById(req, res));

router.put("/transactions/:id", (req, res) => Transactions.updateTransaction(req, res));

router.delete("/transactions/:id", (req, res) => Transactions.deleteTransaction(req, res));

router.post("/transactions", (req, res) => Transactions.createTransaction(req, res));




// Accounts routes
router.get("/accounts", (req, res) => Accounts.getAllAccounts(req, res));

router.get("/accounts/:id", (req, res) => Accounts.getAccountById(req, res));

router.put("/accounts/:id", (req, res) => Accounts.updateAccount(req, res));

router.delete("/accounts/:id", (req, res) => Accounts.deleteAccount(req, res));

router.post("/accounts", (req, res) => Accounts.createAccount(req, res));



// Card routes
router.get("/cards", (req, res) => Card.getAllCards(req, res));

router.get("/cards/:id", (req, res) => Card.getCardById(req, res));

router.put("/cards/:id", (req, res) => Card.updateCard(req, res));

router.delete("/cards/:id", (req, res) => Card.deleteCard(req, res));

router.post("/cards", (req, res) => Card.createCard(req, res));




export default router;
