package br.com.gritti.app.application.mapper;

import br.com.gritti.app.domain.valueobject.Email;

public interface DefaultMapper {
  default Email stringToEmail(String email) {
    return email != null ? new Email(email) : null;
  }

  default String emailToString(Email email) {
    return email != null ? email.getValue() : null;
  }
}
