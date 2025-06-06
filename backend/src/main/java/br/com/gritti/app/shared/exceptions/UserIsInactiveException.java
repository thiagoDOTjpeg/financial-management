package br.com.gritti.app.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserIsInactiveException extends RuntimeException {
  public UserIsInactiveException(String message) {
    super(message);
  }
}
