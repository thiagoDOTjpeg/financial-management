package br.com.gritti.app.interfaces.controller.docs;

import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.domain.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RoleControllerDocs {
  @Operation(summary = "Listar todos as permissões(roles) cadastrados no sistema",
          description =  "Retorna uma lista completa de todos as permissões(roles) registrados no sistema",
          tags = {"Role"},
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<List<Role>> getRoles();

  @Operation(summary = "Buscar permissão (role) específica pelo ID",
          description = "Recupera os detalhes completos de uma permissão (role) específica identificada pelo UUID fornecido, incluindo seu nome, descrição e quaisquer metadados associados.",
          tags = {"Role"},
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = Role.class))
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<Role> getRoleById(UUID id);

  @Operation(summary = "Criar nova permissão (role) no sistema",
          description = "Registra uma nova permissão (role) no sistema com o nome e descrição fornecidos. Esta role poderá posteriormente ser atribuída a usuários para conceder acesso a funcionalidades específicas.",
          tags = {"Role"},
          responses = {
                  @ApiResponse(description = "Created", responseCode = "201", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = Role.class))
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<?> saveRole(Role role);
}
