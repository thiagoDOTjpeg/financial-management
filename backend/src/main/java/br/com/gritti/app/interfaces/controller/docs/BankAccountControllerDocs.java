package br.com.gritti.app.interfaces.controller.docs;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BankAccountControllerDocs {
  @Operation(summary = "Lista as contas bancárias cadastradas.",
          description = "Retorna uma lista paginada de contas bancárias cadastradas no sistema, " +
                  "permitindo paginação e ordenação através dos parâmetros opcionais de página, tamanho e direção da ordenação.",
          tags = {"Bank Account"},
          responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = BankAccountResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<PagedModel<EntityModel<BankAccountResponseDTO>>> getBankAccounts(Integer page, Integer size, String direction, String username);

  @Operation(summary = "Consulta uma conta bancária pelo ID.",
          description = "Busca e retorna os dados detalhados de uma conta bancária específica, de acordo com o identificador (UUID) informado. " +
                  "Caso não seja encontrada, retorna informação apropriada.",
          responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = BankAccountResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<BankAccountResponseDTO> getAccountById(UUID id) ;

  @Operation(summary = "Cadastra uma nova conta bancária.",
          description = "Recebe os dados de uma nova conta bancária e realiza seu cadastro no sistema. Retorna os dados da conta criada em caso de sucesso.",
          responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = BankAccountResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<BankAccountResponseDTO> createAccount(BankAccountCreateDTO bankAccountCreateDTO);

  @Operation(summary = "Atualiza os dados de uma conta bancária existente.",
          description = "Altera as informações de uma conta bancária previamente cadastrada, identificada pelo UUID, " +
                  "conforme os dados informados. Retorna os dados atualizados da conta.",
          responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = BankAccountResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<BankAccountResponseDTO> updateAccount(UUID id, BankAccountUpdateDTO bankAccountDTO);


  @Operation(summary = "Remove uma conta bancária.",
          description = "Exclui uma conta bancária identificada pelo UUID fornecido. Retorna resposta de sucesso ou erro, conforme o caso.",
          responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = {
                  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          array = @ArraySchema(schema = @Schema(implementation = BankAccountResponseDTO.class))),
          }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<?> deleteAccount(UUID id);
}
