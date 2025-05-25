package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.application.dto.card.CardUpdateDTO;
import br.com.gritti.app.application.valueobjects.CardMinimalVO;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper extends DefaultMapper {

  CardResponseDTO cardToCardResponseDTO(Card card);

  CardMinimalVO cardToCardMinimalVO(Card card);

  Card cardCreateDTOtoCard(CardCreateDTO cardCreateDTO);

  Card cardUpdateDTOtoCard(CardUpdateDTO cardUpdateDTO);
}
