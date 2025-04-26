package br.com.gritti.app.interfaces.controller.docs;

import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDocs {

  @Operation(summary = "Autenticar usuário e gerar tokens de acesso",
          description = "Valida as credenciais do usuário (nome de usuário/email e senha) e, se corretas, retorna um Access Token JWT para acesso à API e um Refresh Token para renovação da sessão. O Access Token deve ser incluído no cabeçalho de autorização das requisições subsequentes.",
          tags = {"Auth"},
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = Token.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
  })
  public ResponseEntity<Token> signin(AccountCredentials data);

  @Operation(summary = "Renovar sessão com Refresh Token",
          description = "Gera um novo Access Token utilizando um Refresh Token válido, permitindo que o usuário continue autenticado sem precisar informar suas credenciais novamente. O Refresh Token anterior é invalidado e um novo é gerado junto com o Access Token.",
          tags = {"Auth"},
          responses = {
                  @ApiResponse(description = "Success", responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  array = @ArraySchema(schema = @Schema(implementation = Token.class))),
                  }),
                  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                  @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
          })
  public ResponseEntity<Token> refreshToken(String refreshToken);
}
