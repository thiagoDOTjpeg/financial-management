package br.com.gritti.infra.exception;

import br.com.gritti.shared.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
  private ExceptionMessage createExceptionMessage(String message, HttpStatus status, String details) {
    return new ExceptionMessage(new Date(), status.value(), status.getReasonPhrase(), message, details);
  }

  @ExceptionHandler(BusinessException.class)
  public final ResponseEntity<ExceptionMessage> handleAllBusinessException(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Erro ao aplicar lógica de negócio", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUsernameNotFound(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Usuário não encontrado", HttpStatus.NOT_FOUND, request.getDescription(false)), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionMessage> handleAllExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Um erro inesperado ocorreu", HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidJWTAuthenticationException.class)
  public final ResponseEntity<ExceptionMessage> handleAllJWTExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Token inválido", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserIsInactiveException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUserIsInactive(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("O usuário está desativado", HttpStatus.FORBIDDEN, request.getDescription(false)), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleAllResourceNotFound(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Recurso não encontrado", HttpStatus.NOT_FOUND, request.getDescription(false)), HttpStatus.NOT_FOUND);
  }
}
