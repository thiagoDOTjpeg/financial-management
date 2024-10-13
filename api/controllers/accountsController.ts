import { Request, Response } from "express"
import Account from "../models/Account"

class AccountController {
  async getAllAccounts(req: Request, res: Response) {
    try {
      const accounts = await Account.findAll();
      res.status(200).json(accounts);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }
}

export default new AccountController;