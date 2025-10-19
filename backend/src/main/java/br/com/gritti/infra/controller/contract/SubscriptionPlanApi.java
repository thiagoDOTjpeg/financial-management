package br.com.gritti.infra.controller.contract;
import br.com.gritti.shared.dto.request.subscriptionPlan.CreateSubscriptionPlanRequest;
import br.com.gritti.shared.dto.request.subscriptionPlan.UpdateSubscriptionPlan;
import br.com.gritti.shared.dto.response.SubscriptionPlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Subscription Plans", description = "Endpoints para gerenciamento de planos de assinatura")
@RequestMapping("/api/v1/plans")
public interface SubscriptionPlanApi {

  @Operation(
          summary = "Lista todos os planos de assinatura (paginado)",
          description = "Retorna uma lista paginada e ordenada de planos de assinatura, com links HATEOAS."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Lista de planos retornada com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))), // A resposta é um PagedModel
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping()
  ResponseEntity<PagedModel<EntityModel<SubscriptionPlanResponse>>> getSubscriptionPlans(
          @Parameter(description = "Número da página (base 0)", example = "0")
          @RequestParam(value = "page", defaultValue = "0") Integer page,

          @Parameter(description = "Tamanho da página", example = "12")
          @RequestParam(value = "size", defaultValue = "12") Integer size,

          @Parameter(description = "Direção da ordenação (pelo nome)", example = "asc", schema = @Schema(type = "string", allowableValues = {"asc", "desc"}))
          @RequestParam(value = "direction", defaultValue = "asc") String direction,

          @Parameter(hidden = true)
          PagedResourcesAssembler<SubscriptionPlanResponse> pagedResourcesAssembler
  );

  @Operation(
          summary = "Cria um novo plano de assinatura",
          description = "Cria um novo plano de assinatura no sistema."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Plano criado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionPlanResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (dados faltando ou incorretos)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SubscriptionPlanResponse> createSubscriptionPlan(
          @RequestBody(description = "Dados para criação do novo plano", required = true,
                  content = @Content(schema = @Schema(implementation = CreateSubscriptionPlanRequest.class)))
          @Valid @org.springframework.web.bind.annotation.RequestBody // Spring's
          CreateSubscriptionPlanRequest request
  );

  @Operation(
          summary = "Atualiza um plano de assinatura existente",
          description = "Atualiza os dados de um plano com base no seu ID."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionPlanResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (dados faltando ou incorretos)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Plano não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PutMapping(value = "/{planId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SubscriptionPlanResponse> updatePlan(
          @Parameter(description = "ID do plano a ser atualizado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID planId,

          @RequestBody(description = "Dados para atualização do plano", required = true,
                  content = @Content(schema = @Schema(implementation = UpdateSubscriptionPlan.class)))
          @Valid @org.springframework.web.bind.annotation.RequestBody // Spring's
          UpdateSubscriptionPlan request
  );

  @Operation(
          summary = "Desativa um plano de assinatura",
          description = "Realiza a desativação lógica de um plano (soft delete)."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Plano desativado com sucesso", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Plano não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PatchMapping(value = "/{planId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> deactivatePlan(
          @Parameter(description = "ID do plano a ser desativado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID planId
  );
}