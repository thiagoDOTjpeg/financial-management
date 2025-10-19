package br.com.gritti.infra.controller.contract;

import br.com.gritti.domain.vo.AccountCredentials;
import br.com.gritti.domain.vo.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints para autenticação de usuários e gerenciamento de tokens")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

  @Operation(
          summary = "Autentica um usuário",
          description = "Autentica um usuário com base no nome de usuário e senha, retornando um token de acesso e um token de atualização (refresh token)."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: campos faltando)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
  })
  @PostMapping("/signin")
  ResponseEntity<Token> signin(
          @RequestBody(description = "Credenciais da conta do usuário", required = true,
                  content = @Content(schema = @Schema(implementation = AccountCredentials.class)))
          @org.springframework.web.bind.annotation.RequestBody
          AccountCredentials data
  );

  @Operation(
          summary = "Atualiza o token de acesso",
          description = "Usa um refresh token válido (enviado no header 'Authorization') para emitir um novo token de acesso."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
          @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: faltando token)", content = @Content),
          @ApiResponse(responseCode = "401", description = "Token de atualização inválido ou expirado", content = @Content)
  })
  @PostMapping("/refresh")
  ResponseEntity<Token> refreshToken(
          @Parameter(name = "Authorization", description = "O refresh token (prefixado com 'Bearer ')",
                  required = true, in = ParameterIn.HEADER, schema = @Schema(type = "string"))
          @RequestHeader("Authorization")
          String refreshToken
  );
}