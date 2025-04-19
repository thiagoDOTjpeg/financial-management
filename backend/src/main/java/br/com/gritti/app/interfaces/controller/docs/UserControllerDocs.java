package br.com.gritti.app.interfaces.controller.docs;

import br.com.gritti.app.application.dto.UserAssignRoleDTO;
import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.UUID;

public interface UserControllerDocs {

  @Operation(summary = "Obter todos os usuários",
              description =  "Obter todos os usuários",
              tags = {"Users"},
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
  public ResponseEntity<List<UserResponseDTO>> getUsers();

  @Operation(summary = "Obtém um usuário pelo ID",
          description =  "Obtém um usuário pelo ID",
          tags = {"Users"},
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

  @Operation(summary = "Adiciona um Role em um usuário pelo nome da Role e pelo UUID do usuário",
          description =  "Adiciona um Role em um usuário pelo nome da Role e pelo UUID do usuário",
          tags = {"Users"},
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

  @Operation(summary = "Cria um usuário novo",
          description =  "Cria um usuário novo",
          tags = {"Users"},
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

  @Operation(summary = "Deleta um usuário",
          description =  "Deleta um usuário",
          tags = {"Users"},
          responses = {
                  @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<?> deleteUser(UUID id);

  @Operation(summary = "Desativa um usuário",
          description =  "Desativa um usuário",
          tags = {"Users"},
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
