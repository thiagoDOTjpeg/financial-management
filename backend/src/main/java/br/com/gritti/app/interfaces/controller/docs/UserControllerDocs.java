package br.com.gritti.app.interfaces.controller.docs;

import br.com.gritti.app.application.dto.UserAssignRoleDTO;
import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.dto.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.UUID;

public interface UserControllerDocs {

  @Operation(summary = "Listar todos os usuários cadastrados no sistema",
              description =  "Retorna uma lista completa de todos os usuários ativos e inativos registrados no sistema, incluindo suas informações básicas e roles associadas",
              tags = {"User"},
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
  public ResponseEntity<CollectionModel<UserResponseDTO>> getUsers();

  @Operation(summary = "Buscar usuário específico pelo ID",
          description =  "Retorna os dados detalhados de um único usuário identificado pelo UUID fornecido, incluindo informações pessoais e permissões associadas",
          tags = {"User"},
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
  public ResponseEntity<UserResponseDTO> getUserById(UUID id);

  @Operation(summary = "Atribuir permissão (role) a um usuário",
          description =  "Adiciona uma nova permissão (role) ao usuário identificado pelo UUID, permitindo acesso a funcionalidades específicas do sistema conforme o nível de autorização da role",
          tags = {"User"},
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
  public ResponseEntity<UserResponseDTO> assignRoleToUser(UserAssignRoleDTO data, UUID id);

  @Operation(summary = "Cadastrar novo usuário no sistema",
          description =  "Cria um novo registro de usuário no sistema com os dados fornecidos, incluindo informações pessoais e credenciais de acesso. Um UUID único será gerado automaticamente",
          tags = {"User"},
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
  public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO userDTO);

  @Operation(summary = "Atualizar dados de um usuário existente",
          description =  "Modifica informações específicas de um usuário identificado pelo UUID. Apenas campos permitidos podem ser alterados, mantendo a integridade dos dados sensíveis do usuário",
          tags = {"User"},
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
  public ResponseEntity<UserResponseDTO> updateUser(UUID id, UserUpdateDTO userDTO);

  @Operation(summary = "Remover usuário permanentemente do sistema",
          description =  "Exclui definitivamente o registro do usuário identificado pelo UUID, removendo todas as informações e associações relacionadas no banco de dados",
          tags = {"User"},
          responses = {
                  @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<?> deleteUser(UUID id);

  @Operation(summary = "Desativar conta de usuário",
          description =  "Altera o status do usuário identificado pelo UUID para inativo, mantendo suas informações no banco de dados, mas impedindo seu acesso ao sistema até que seja reativado",
          tags = {"User"},
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
  public ResponseEntity<UserResponseDTO> inactivateUser(UUID id);

}
