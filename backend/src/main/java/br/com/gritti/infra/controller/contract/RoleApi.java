package br.com.gritti.infra.controller.contract;
import br.com.gritti.shared.dto.request.role.CreateRoleRequest;
import br.com.gritti.shared.dto.request.role.UpdateRoleRequest;
import br.com.gritti.shared.dto.response.RoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Roles", description = "Endpoints para gerenciamento de perfis de usuário (roles)")
@RequestMapping("api/v1/roles")
public interface RoleApi {

  @Operation(
          summary = "Cria um novo perfil",
          description = "Cria um novo perfil (role) no sistema com base nos dados fornecidos."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: nome de perfil já existe)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PostMapping
  ResponseEntity<RoleResponse> createRole(
          @RequestBody(description = "Dados para criação do perfil", required = true,
                  content = @Content(schema = @Schema(implementation = CreateRoleRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          CreateRoleRequest request
  );

  @Operation(
          summary = "Atualiza um perfil existente",
          description = "Atualiza os dados de um perfil existente identificado pelo seu ID."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Perfil não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PutMapping("/{id}")
  ResponseEntity<RoleResponse> updateRole(
          @Parameter(description = "ID do perfil a ser atualizado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id,
          @RequestBody(description = "Dados para atualização do perfil", required = true,
                  content = @Content(schema = @Schema(implementation = UpdateRoleRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          UpdateRoleRequest request
  );

  @Operation(
          summary = "Busca um perfil pelo ID",
          description = "Retorna um perfil específico com base no seu ID."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Perfil encontrado",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Perfil não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping("/{id}")
  ResponseEntity<RoleResponse> getById(
          @Parameter(description = "ID do perfil a ser buscado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id
  );

  @Operation(
          summary = "Lista todos os perfis",
          description = "Retorna uma lista de todos os perfis (roles) disponíveis no sistema."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Lista de perfis retornada com sucesso",
                  content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class)))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping
  ResponseEntity<List<RoleResponse>> getAllRoles();

  @Operation(
          summary = "Exclui um perfil",
          description = "Exclui um perfil (role) do sistema com base no seu ID."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Perfil excluído com sucesso", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Perfil não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteRole(
          @Parameter(description = "ID do perfil a ser excluído", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id
  );
}