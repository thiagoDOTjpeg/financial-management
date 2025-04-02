package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "email", source = "email")
  UserCreateDTO userToUserCreateDTO(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "email", source = "email")
  User userCreateDTOtoUser(UserCreateDTO userCreateDTO);

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
