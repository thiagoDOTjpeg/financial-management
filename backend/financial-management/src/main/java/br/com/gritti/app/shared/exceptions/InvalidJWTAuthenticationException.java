package br.com.gritti.app.shared.exceptions;

public class InvalidJWTAuthenticationException extends RuntimeException {
  public InvalidJWTAuthenticationException(String message) {
    super(message);
  }
}
