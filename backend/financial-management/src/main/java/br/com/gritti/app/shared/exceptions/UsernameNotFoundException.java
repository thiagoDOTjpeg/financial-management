package br.com.gritti.app.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String message) {
    super(message);
  }
}
