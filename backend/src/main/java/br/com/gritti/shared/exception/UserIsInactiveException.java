package br.com.gritti.shared.exception;

public class UserIsInactiveException extends RuntimeException {
  public UserIsInactiveException(String message) {
    super(message);
  }
}
