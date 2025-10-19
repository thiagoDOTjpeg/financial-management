package br.com.gritti.infra.controller.contract;
import br.com.gritti.infra.security.AuthenticatedUserId;
import br.com.gritti.shared.dto.request.user.CreateUserRequest;
import br.com.gritti.shared.dto.request.user.UpdateUserRequest;
import br.com.gritti.shared.dto.response.UserResponse;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Users", description = "Endpoints para gerenciamento de usuários")
@RequestMapping("/api/v1/users")
public interface UserApi {

  @Operation(
          summary = "Cria um novo usuário",
          description = "Registra um novo usuário no sistema."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (dados faltando ou incorretos)", content = @Content),
          @ApiResponse(responseCode = "409", description = "Conflito (ex: username ou email já existem)", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PostMapping
  ResponseEntity<UserResponse> createUser(
          @RequestBody(description = "Dados para criação do usuário", required = true,
                  content = @Content(schema = @Schema(implementation = CreateUserRequest.class)))
          @Valid @org.springframework.web.bind.annotation.RequestBody // Spring's
          CreateUserRequest request
  );

  @Operation(
          summary = "Busca um usuário pelo ID",
          description = "Retorna um usuário específico com base no seu ID. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping("/{id}")
  ResponseEntity<UserResponse> getUserById(
          @Parameter(description = "ID do usuário a ser buscado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id
  );

  @Operation(
          summary = "Busca um usuário pelo username",
          description = "Retorna um usuário específico com base no seu nome de usuário. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping("/username/{username}")
  ResponseEntity<UserResponse> getUserByUsername(
          @Parameter(description = "Nome de usuário a ser buscado", required = true)
          @PathVariable String username
  );

  @Operation(
          summary = "Lista todos os usuários (paginado)",
          description = "Retorna uma lista paginada e ordenada de usuários, com links HATEOAS. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PagedModel<EntityModel<UserResponse>>> getAllUsers(
          @Parameter(hidden = true)
          @AuthenticatedUserId UUID userId,

          @Parameter(description = "Número da página (base 0)", example = "0")
          @RequestParam(value = "page", defaultValue = "0") Integer page,

          @Parameter(description = "Tamanho da página", example = "12")
          @RequestParam(value = "size", defaultValue = "12") Integer size,

          @Parameter(description = "Direção da ordenação (pelo username)", example = "asc", schema = @Schema(type = "string", allowableValues = {"asc", "desc"}))
          @RequestParam(value = "direction", defaultValue = "asc") String direction,

          @Parameter(hidden = true) // Oculta o assembler da documentação
          PagedResourcesAssembler<UserResponse> pagedResourcesAssembler
  );

  @Operation(
          summary = "Atualiza um usuário existente",
          description = "Atualiza os dados de um usuário específico pelo seu ID. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
          @ApiResponse(responseCode = "409", description = "Conflito (ex: novo username ou email já existem)", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @PutMapping("/{id}")
  ResponseEntity<UserResponse> updateUser(
          @Parameter(description = "ID do usuário a ser atualizado", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id,
          @RequestBody(description = "Dados para atualização do usuário", required = true,
                  content = @Content(schema = @Schema(implementation = UpdateUserRequest.class)))
          @Valid @org.springframework.web.bind.annotation.RequestBody // Spring's
          UpdateUserRequest request
  );

  @Operation(
          summary = "Exclui um usuário",
          description = "Exclui (soft delete) um usuário do sistema pelo seu ID. (Requer autenticação)",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso", content = @Content),
          @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
          @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content),
          @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteUser(
          @Parameter(description = "ID do usuário a ser excluído", required = true, schema = @Schema(type = "string", format = "uuid"))
          @PathVariable UUID id
  );

  @Operation(
          summary = "Verifica a existência de um username",
          description = "Verifica se um nome de usuário já está em uso no sistema."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retorna true se o username existe, false caso contrário",
                  content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping("/exists/username/{username}")
  ResponseEntity<Boolean> existsByUsername(
          @Parameter(description = "Nome de usuário a ser verificado", required = true)
          @PathVariable String username
  );

  @Operation(
          summary = "Verifica a existência de um email",
          description = "Verifica se um endereço de email já está em uso no sistema."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Retorna true se o email existe, false caso contrário",
                  content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
  })
  @GetMapping("/exists/email/{email}")
  ResponseEntity<Boolean> existsByEmail(
          @Parameter(description = "Endereço de email a ser verificado", required = true)
          @PathVariable String email
  );
}