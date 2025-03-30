package br.com.gritti.app.domain.valueobject;

import br.com.gritti.app.shared.exceptions.InvalidEmailException;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {
  private String value;

  private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

  public Email(String value)  {
    if (value == null || !Pattern.matches(EMAIL_REGEX, value)) {
      throw new InvalidEmailException("Invalid email format: " + value);
    }
    this.value = value;
  }

  public Email() {
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return Objects.equals(value, email.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
