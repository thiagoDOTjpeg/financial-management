import { Request, Response } from "express"
import models from "../models/index"

class AccountController {
  async getAllAccounts(req: Request, res: Response) {
    try {
      const accounts = await models.Account.findAll();
      res.status(200).json(accounts);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async getAccountById(req: Request, res: Response) {
    try {
      const account = await models.Account.findByPk(req.params.id);
      if (account === null) {
        res.status(404).json({ "message": "Conta não encontrada" });
      } else {
        res.status(200).json(account);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async createAccount(req: Request, res: Response) {
    try {
      const account = await models.Account.create(req.body);
      res.status(201).json(account);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao criar a conta", "error": error })
    }
  }

  async updateAccount(req: Request, res: Response) {
    try {
      const account = await models.Account.findByPk(req.params.id);
      if (account === null) {
        res.status(404).json({ "message": "Conta não encontrada" });
      } else {
        await account.update(req.body, {
          where: { id_conta: req.params.id },
          returning: true
        });
        res.status(200).json(account);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao atualizar a conta", "error": error })
    }
  }

  async deleteAccount(req: Request, res: Response) {
    try {
      const account = await models.Account.findByPk(req.params.id);
      if (account === null) {
        res.status(404).json({ "message": "Conta não encontrada" });
      } else {
        await account.destroy();
        res.status(200).json({ "message": "Conta deletada com sucesso" });
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao deletar a conta", "error": error })
    }
  }
}

export default new AccountController;