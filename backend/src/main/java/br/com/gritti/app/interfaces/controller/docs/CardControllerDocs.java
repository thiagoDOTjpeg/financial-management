package br.com.gritti.app.interfaces.controller.docs;


import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.card.CardCreateDTO;
import br.com.gritti.app.application.dto.card.CardResponseDTO;
import br.com.gritti.app.application.dto.card.CardUpdateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public interface CardControllerDocs {
  @Operation(summary = "Lista todos os cartões cadastrados",
  description = "Retorna uma lista pagina de cartões no sistema, " +
          "permitindo paginação e ordenação através dos parâmetros opcionais de página, tamanho e direção da ordenação.",
  tags = {"Card"},
  responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = CardResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<PagedModel<EntityModel<CardResponseDTO>>> getCards(Integer page, Integer size,
                                                                           String direction,String username
  );

  @Operation(summary = "Consulta um cartão através do seu ID",
          description = "Retorna uma lista pagina de cartões no sistema, " +
                  "permitindo paginação e ordenação através dos parâmetros opcionais de página, tamanho e direção da ordenação.",
          tags = {"Card"},
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = CardResponseDTO.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<CardResponseDTO> getCardById(UUID id);

    @Operation(summary = "Consulta um cartão através do seu ID",
            description = "Retorna uma lista pagina de cartões no sistema, " +
                    "permitindo paginação e ordenação através dos parâmetros opcionais de página, tamanho e direção da ordenação.",
            tags = {"Card"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CardResponseDTO.class))),
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<List<TransactionResponseDTO>> createCardTransaction(UUID id, TransactionCreateDTO dto) throws BadRequestException;

  @Operation(summary = "Remove um cartão",
          description = "Exclui cartão identificada pelo UUID fornecido. Retorna resposta de sucesso ou erro, conforme o caso.",
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200"),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<?> deleteCard(UUID id);

  @Operation(summary = "Cadastra um novo cartão",
          description = "Recebe os dados de um novo cartão e realiza seu cadastro no sistema. Retorna os dados do cartão criada em caso de sucesso.",
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = CardResponseDTO.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<CardResponseDTO> createCard(CardCreateDTO cardCreateDTO, UUID bankAccountId);

  @Operation(summary = "Atualiza os dados de um cartão existente.",
          description = "Altera as informações de um cartão previamente cadastrada, identificada pelo UUID, " +
                  "conforme os dados informados. Retorna os dados atualizados da conta.",
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = CardResponseDTO.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public CardResponseDTO updateCard(UUID id, CardUpdateDTO cardUpdateDTO);
}
