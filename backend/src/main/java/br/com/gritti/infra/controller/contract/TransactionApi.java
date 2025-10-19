package br.com.gritti.infra.controller.contract;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.transaction.CreateTransactionRequest;
import br.com.gritti.shared.dto.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Transactions", description = "Endpoints para gerenciamento de transações financeiras")
@RequestMapping("/api/v1/transactions")
public interface TransactionApi {

  @Operation(
          summary = "Lista todas as transações (paginado)",
          description = "Retorna uma lista paginada e ordenada de transações, com links HATEOAS. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PagedModel<EntityModel<TransactionResponse>>> getAlTransactions(
          @Parameter(description = "Número da página (base 0)", example = "0")
          @RequestParam(value = "page", defaultValue = "0") Integer page,

          @Parameter(description = "Tamanho da página", example = "12")
          @RequestParam(value = "size", defaultValue = "12") Integer size,

          @Parameter(description = "Direção da ordenação (pela data)", example = "asc", schema = @Schema(type = "string", allowableValues = {"asc", "desc"}))
          @RequestParam(value = "direction", defaultValue = "asc") String direction,

          @Parameter(hidden = true)
          PagedResourcesAssembler<TransactionResponse> pagedResourcesAssembler
  );

  @Operation(
          summary = "Cria uma nova transação",
          description = "Cria uma nova transação associada ao usuário autenticado.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Transação criada com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (dados faltando ou incorretos)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<TransactionResponse> createTransaction(
          @Parameter(hidden = true)
          @AuthenticatedUserId UUID userId,

          @RequestBody(description = "Dados da transação a ser criada", required = true,
                  content = @Content(schema = @Schema(implementation = CreateTransactionRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          CreateTransactionRequest request
  );
}