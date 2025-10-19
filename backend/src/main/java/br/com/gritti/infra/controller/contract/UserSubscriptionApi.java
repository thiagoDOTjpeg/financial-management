package br.com.gritti.infra.controller.contract;
import br.com.gritti.shared.dto.request.userSubscription.CreateUserSubscriptionRequest;
import br.com.gritti.shared.dto.response.UserSubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User Subscriptions", description = "Endpoints para gerenciar as assinaturas (planos) dos usuários")
@RequestMapping("api/v1/subscriptions")
public interface UserSubscriptionApi {

  @Operation(
          summary = "Cria uma nova assinatura para o usuário",
          description = "Associa um plano de assinatura ao usuário autenticado.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Assinatura criada com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSubscriptionResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (plano não existe, etc)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Plano não encontrado", content = @Content),
          @ApiResponse(responseCode = "409", description = "Usuário já possui uma assinatura ativa", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<UserSubscriptionResponse> createSubscription(
          @Parameter(hidden = true)
          UUID userId,

          @RequestBody(description = "Dados para criação da assinatura (ex: ID do plano)", required = true,
                  content = @Content(schema = @Schema(implementation = CreateUserSubscriptionRequest.class)))
          @Valid @org.springframework.web.bind.annotation.RequestBody
          CreateUserSubscriptionRequest request
  );

  @Operation(
          summary = "Busca a assinatura do usuário autenticado",
          description = "Retorna os detalhes da assinatura ativa do usuário logado.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Assinatura encontrada",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSubscriptionResponse.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Usuário não possui assinatura", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping(value = "/my-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<UserSubscriptionResponse> getUserSubscriptionById(
          @Parameter(hidden = true)
          UUID userId
  );

  @Operation(
          summary = "Cancela uma assinatura",
          description = "Cancela a assinatura de um usuário (requer permissão de admin).",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Assinatura cancelada com sucesso", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Assinatura ou usuário não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PatchMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> cancelSubscription(
          @Parameter(description = "ID do usuário (ou assinatura) a ser cancelada", required = true)
          @RequestParam UUID userId
  );
}