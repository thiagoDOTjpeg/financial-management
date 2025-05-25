package br.com.gritti.app.shared.exceptions;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date timestamp;
  private int status;
  private String error;
  private String message;
  private String details;
  private Map<String, String> validationErrors = new HashMap<>();

  public ExceptionMessage(Date timestamp, int status, String error, String message, String details) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.details = details;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }

  public Map<String, String> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(Map<String, String> validationErrors) {
    this.validationErrors = validationErrors;
  }
}
