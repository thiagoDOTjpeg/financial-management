package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.dto.UserUpdateDTO;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "email", source = "email")
  UserCreateDTO userToUserCreateDTO(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", source = "email")
  User userCreateDTOtoUser(UserCreateDTO userCreateDTO);

  default void updateUser(User user, User entity) {
    boolean changed = false;
    if(user.getUsername() != null) {
      entity.setUsername(user.getUsername());
      changed = true;
    }
    if(user.getFullName() != null) {
      entity.setFullName(user.getFullName());
      changed = true;
    }
    if(user.getEmail().getValue() != null) {
      changed = true;
      entity.setEmail(user.getEmail());
    }
    if(user.getPassword() != null) {
      changed = true;
      entity.setPassword(user.getPassword());
    }
    if(!changed) {
      throw new IllegalArgumentException("Only Username/Fullname/Password and Email are allowed to change");
    }
  }

  User userUpdateDTOtoUser(UserUpdateDTO userUpdateDTO);

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  UserResponseDTO userToUserResponseDTO(User user);

  default UserResponseDTO userToUserResponseDTOPermissionCheck(User user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserResponseDTO dto = userToUserResponseDTO(user);
    if(authentication != null && authentication.getAuthorities() != null &&
            authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
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
