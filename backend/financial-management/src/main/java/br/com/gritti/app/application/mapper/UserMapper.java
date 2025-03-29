package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "email", source = "email")
  UserCreateDTO toUserCreateDTO(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", source = "email")
  User toUser(UserCreateDTO userCreateDTO);

  default Email stringToEmail(String email) {
    return email != null ? new Email(email) : null;
  }

  default String emailToString(Email email) {
    return email != null ? email.getValue() : null;
  }
}
