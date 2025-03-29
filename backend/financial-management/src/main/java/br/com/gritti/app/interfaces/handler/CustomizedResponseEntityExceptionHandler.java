package br.com.gritti.app.interfaces.handler;

import br.com.gritti.app.shared.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionMessage> handleAllExceptions(Exception ex, WebRequest request) {
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidEmailException.class)
  public final ResponseEntity<ExceptionMessage> handleAllEmailExceptions(Exception ex, WebRequest request) {
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUsernameExceptions(Exception ex, WebRequest request) {
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleNotFoundExceptions(Exception ex, WebRequest request) {
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidJWTAuthenticationException.class)
  public final ResponseEntity<ExceptionMessage> handleInvalidJWTAuthenticationException(Exception ex, WebRequest request) {
    ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
  }
}
