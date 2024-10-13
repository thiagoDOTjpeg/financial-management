import { Request, Response } from "express"
import Transaction from "../models/transaction"

class TransactionController {
  async getAllTransaction(req: Request, res: Response) {
    try {
      const transaction = await Transaction.findAll();
      res.status(200).json(transaction);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async getTransactionById(req: Request, res: Response) {
    try {
      const transaction = await Transaction.findByPk(req.params.id);
      if (transaction === null) {
        res.status(404).json({ "message": "Transação não encontrada" });
      } else {
        res.status(200).json(transaction);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async createTransaction(req: Request, res: Response) {
    try {
      const transaction = await Transaction.create(req.body);
      res.status(201).json(transaction);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao criar a transação", "error": error })
    }
  }

  async updateTransaction(req: Request, res: Response) {
    try {
      const transaction = await Transaction.findByPk(req.params.id);
      if (transaction === null) {
        res.status(404).json({ "message": "Transação não encontrada" });
      } else {
        await transaction.update(req.body, {
          where: { id_transacoes: req.params.id },
          returning: true
        });
        res.status(200).json(transaction);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao atualizar a transação", "error": error })
    }
  }

  async deleteTransaction(req: Request, res: Response) {
    try {
      const transaction = await Transaction.findByPk(req.params.id);
      if (transaction === null) {
        res.status(404).json({ "message": "Transação não encontrada" });
      } else {
        await transaction.destroy();
        res.status(200).json({ "message": "Transação deletada com sucesso" });
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao deletar a transação", "error": error })
    }
  }
}


export default new TransactionController;
