package br.com.gritti.app.interfaces.handler;

import br.com.gritti.app.shared.exceptions.*;
import jakarta.security.auth.message.AuthException;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

  private ExceptionMessage createExceptionMessage(String message, HttpStatus status, String details) {
    return new ExceptionMessage(new Date(), status.value(), status.getReasonPhrase(), message, details);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionMessage> handleAllExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("An excepted error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<ExceptionMessage> handleAllAccessDeniedExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("An excepted error occurred", HttpStatus.FORBIDDEN, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public final ResponseEntity<ExceptionMessage> handleAllHttpMessageNotReadableExceptions(Exception  ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage(ex.getMessage() ,HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ExceptionMessage> handleAllBadRequestException(Exception  ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage(ex.getMessage() ,HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserIsInactiveException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUserIsInactiveException(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("The user is Inactive" ,HttpStatus.FORBIDDEN, request.getDescription(false)), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidEmailException.class)
  public final ResponseEntity<ExceptionMessage> handleAllEmailExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Email is invalid", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidPaymentTypeException.class)
  public final ResponseEntity<ExceptionMessage> handleAllInvalidPaymentTypeExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Payment Type is invalid", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidBalanceException.class)
  public final ResponseEntity<ExceptionMessage> handleAllInvalidBalanceExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Invalid Balance", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public final ResponseEntity<ExceptionMessage> handleAllBadCredentialsExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("The provided data is incorrect or doesn't exist", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public final ResponseEntity<ExceptionMessage> handleAllEmailAlreadyExistsExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("The Email already exists in the database", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUsernameAlreadyExistsExceptions(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("The Username already exists in the database", HttpStatus.BAD_REQUEST, request.getDescription(false)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleAllUsernameExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Username Not Found", HttpStatus.NOT_FOUND, request.getDescription(false)), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionMessage> handleNotFoundExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Resource not found", HttpStatus.NOT_FOUND, request.getDescription(false)), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidJWTAuthenticationException.class)
  public final ResponseEntity<ExceptionMessage> handleInvalidJWTAuthenticationException(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("The AccessToken or RefreshToken is invalid", HttpStatus.UNAUTHORIZED, request.getDescription(false)), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ExceptionMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> validationErrors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    ExceptionMessage exceptionMessage = createExceptionMessage("Validation failed", HttpStatus.BAD_REQUEST, request.getDescription(false));
    exceptionMessage.setValidationErrors(validationErrors);
    return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public final ResponseEntity<ExceptionMessage> handleNullPointerExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(createExceptionMessage("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
