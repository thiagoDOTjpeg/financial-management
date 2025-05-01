package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.user.UserCreateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.dto.user.UserUpdateDTO;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "email", source = "email")
  UserCreateDTO userToUserCreateDTO(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", source = "email")
  User userCreateDTOtoUser(UserCreateDTO userCreateDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "authorities", ignore = true)
  void updateUser(User updatedUser, @MappingTarget User user);

  User userUpdateDTOtoUser(UserUpdateDTO userUpdateDTO);

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "roles", ignore = true)
  UserResponseDTO userToUserResponseDTO(User user);

  default UserResponseDTO userToUserResponseDTO(User user, boolean isAdmin) {
    UserResponseDTO dto = userToUserResponseDTO(user);
    if(isAdmin) {
      dto.setRoles(user.getPermissions());
      dto.setCreatedAt(user.getCreatedAt());
      dto.setUpdatedAt(user.getUpdatedAt());
      dto.setCreatedBy(user.getCreatedBy());
      dto.setUpdatedBy(user.getUpdatedBy());

    }
    return dto;
  }

  default Email stringToEmail(String email) {
    return email != null ? new Email(email) : null;
  }

  default String emailToString(Email email) {
    return email != null ? email.getValue() : null;
  }
}
