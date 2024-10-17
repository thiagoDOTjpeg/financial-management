import { Request, Response } from "express";
import models from "../models/index";


class InvoiceController {
  async getAllInvoices(req: Request, res: Response) {
    try {
      const invoices = await models.Invoice.findAll();
      res.status(200).json(invoices);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async getInvoiceById(req: Request, res: Response) {
    try {
      const invoice = await models.Invoice.findByPk(req.params.id);
      if (invoice === null) {
        res.status(404).json({ "message": "Fatura não encontrada" });
      } else {
        res.status(200).json(invoice);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async createInvoice(req: Request, res: Response) {
    try {
      const invoice = await models.Invoice.create(req.body);
      res.status(201).json(invoice);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao criar a fatura", "error": error })
    }
  }

  async updateInvoice(req: Request, res: Response) {
    try {
      const invoice = await models.Invoice.findByPk(req.params.id);
      if (invoice === null) {
        res.status(404).json({ "message": "Fatura não encontrada" });
      } else {
        await invoice.update(req.body, {
          where: { id_fatura: req.params.id },
          returning: true
        });
        res.status(200).json(invoice);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao atualizar a fatura", "error": error })
    }
  }

  async deleteInvoice(req: Request, res: Response) {
    try {
      const invoice = await models.Invoice.findByPk(req.params.id);
      if (invoice === null) {
        res.status(404).json({ "message": "Fatura não encontrada" });
      } else {
        await invoice.destroy();
        res.status(200).json({ "message": "Fatura deletada com sucesso" });
      }
    } catch (error) {
      res.status(500).json({
        "message": "Ocorreu um erro ao deletar a fatura", "error": error
      })
    }
  }



  async getInvoicesTransactions(req: Request, res: Response) {
    try {
      const cardInvoice = await models.Invoice.findByPk(req.params.id, {
        include: models.Transaction
      });
      if (cardInvoice === null) {
        res.status(404).json({ "message": "Transações da fatura não encontrado" });
      } else {
        res.status(200).json(cardInvoice);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar as transações", "error": error })
    }
  }
}

export default new InvoiceController();
