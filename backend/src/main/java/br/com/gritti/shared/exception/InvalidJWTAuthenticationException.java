package br.com.gritti.shared.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTAuthenticationException extends AuthenticationException {
  public InvalidJWTAuthenticationException(String message) {
    super(message);
  }
}
