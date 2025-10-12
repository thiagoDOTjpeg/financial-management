package br.com.gritti.shared.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resourceName, UUID id) {
    super(String.format("%s não encontrado com ID: %s", resourceName, id));
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
