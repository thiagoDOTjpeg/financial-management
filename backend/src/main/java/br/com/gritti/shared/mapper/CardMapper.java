package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Card;
import br.com.gritti.shared.dto.response.CardResponse;
import br.com.gritti.shared.dto.response.summary.CardSummaryResponse;

public class CardMapper {
  public static CardResponse toResponse(Card card) {
    CardResponse response = new CardResponse();
    response.setActive(card.getActive());
    response.setCardBrand(card.getCardBrand());
    response.setCardName(card.getCardName());
    response.setClosingDay(card.getClosingDay());
    response.setDueDay(card.getDueDay());
    response.setCreditLimit(card.getCreditLimit());
    response.setBankAccount(BankAccountMapper.toResponse(card.getBankAccount()));
    return response;
  }

  public static CardSummaryResponse toSummaryResponse(Card card) {
    CardSummaryResponse response = new CardSummaryResponse();
    response.setId(card.getId());
    response.setActive(card.getActive());
    response.setCardBrand(card.getCardBrand());
    response.setCardName(card.getCardName());
    response.setClosingDay(card.getClosingDay());
    response.setDueDay(card.getDueDay());
    response.setCreditLimit(card.getCreditLimit());
    return response;
  }
}
