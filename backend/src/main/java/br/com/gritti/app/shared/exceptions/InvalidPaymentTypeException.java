package br.com.gritti.app.shared.exceptions;

public class InvalidPaymentTypeException extends RuntimeException {
  public InvalidPaymentTypeException(String message) {
    super(message);
  }
}
