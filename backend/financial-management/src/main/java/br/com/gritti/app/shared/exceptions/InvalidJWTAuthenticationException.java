package br.com.gritti.app.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJWTAuthenticationException extends AuthenticationException {
  public InvalidJWTAuthenticationException(String message) {
    super(message);
  }
}
