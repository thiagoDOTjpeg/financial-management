package br.com.gritti.app.shared.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date timestamp;
  private String message;
  private String details;

  public ExceptionMessage(Date timestamp, String message, String details) {
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }
}
