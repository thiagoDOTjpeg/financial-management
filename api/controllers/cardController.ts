import { Request, Response } from "express";
import models from "../models/index";


class CardController {
  async getAllCards(req: Request, res: Response) {
    try {
      const cards = await models.Card.findAll();
      res.status(200).json(cards);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async getCardById(req: Request, res: Response) {
    try {
      const card = await models.Card.findByPk(req.params.id);
      if (card === null) {
        res.status(404).json({ "message": "Cartão não encontrado" });
      } else {
        res.status(200).json(card);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao buscar os dados", "error": error })
    }
  }

  async createCard(req: Request, res: Response) {
    try {
      const card = await models.Card.create(req.body);
      res.status(201).json(card);
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao criar o cartão", "error": error })
    }
  }

  async updateCard(req: Request, res: Response) {
    try {
      const card = await models.Card.findByPk(req.params.id);
      if (card === null) {
        res.status(404).json({ "message": "Cartão não encontrado" });
      } else {
        await card.update(req.body, {
          where: { id_cartao: req.params.id },
          returning: true
        });
        res.status(200).json(card);
      }
    } catch (error) {
      res.status(500).json({ "message": "Ocorreu um erro ao atualizar o cartão", "error": error })
    }
  }

  async deleteCard(req: Request, res: Response) {
    try {
      const card = await models.Card.findByPk(req.params.id);
      if (card === null) {
        res.status(404).json({ "message": "Cartão não encontrado" });
      } else {
        await card.destroy();
        res.status(200).json({ "message": "Cartão deletado com sucesso" });
      }
    } catch (error) {
      res.status(500).json({
        "message": "Ocorreu um erro ao deletar o cartão"
      })
    }
  }
}

export default new CardController();
