package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.user.UserBankAccountsResponseDTO;
import br.com.gritti.app.application.dto.user.UserCreateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.dto.user.UserUpdateDTO;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.*;

import static br.com.gritti.app.application.util.AdminUtil.applyAdminProperties;

@Mapper(componentModel = "spring")
public interface UserMapper extends DefaultMapper{


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
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "fullName", ignore = true)
  UserResponseDTO userToUserResponseDTO(User user);

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "fullName", ignore = true)
  UserBankAccountsResponseDTO userToUserBankAccountsResponseDTO(User user);

  default UserBankAccountsResponseDTO userToUserBankAccountsResponseDTO(User user, boolean isAdmin) {
    UserBankAccountsResponseDTO dto = userToUserBankAccountsResponseDTO(user);
    if(isAdmin) {
      applyAdminProperties(user, dto);
    }
    return dto;
  }

  default UserResponseDTO userToUserResponseDTO(User user, boolean isAdmin) {
    UserResponseDTO dto = userToUserResponseDTO(user);
    if(isAdmin) {
      applyAdminProperties(user, dto);
    }
    return dto;
  }
}
