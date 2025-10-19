package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Role;
import br.com.gritti.domain.model.User;
import br.com.gritti.shared.dto.request.user.CreateUserRequest;
import br.com.gritti.shared.dto.response.UserResponse;
import br.com.gritti.shared.dto.response.summary.UserSummaryResponse;

import java.util.stream.Collectors;

public class UserMapper {
  private UserMapper() {
  }

  public static User toEntity(CreateUserRequest request, String encodedPassword) {
    return new User.Builder()
            .username(request.username())
            .email(request.email())
            .password(encodedPassword)
            .fullName(request.fullName())
            .build();
  }

  public static UserResponse toResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setFullName(user.getFullName());
    response.setAccountStatus(user.getAccountStatus());
    response.setAccountNonExpired(user.getAccountNonExpired());
    response.setAccountNonLocked(user.getAccountNonLocked());
    response.setCredentialsNonExpired(user.getCredentialsNonExpired());
    response.setLastLogin(user.getLastLogin());
    response.setCreatedAt(user.getCreatedAt());
    response.setUpdatedAt(user.getUpdatedAt());

    if (user.getRoles() != null) {
      response.setRoles(
              user.getRoles().stream()
                      .map(Role::getName)
                      .collect(Collectors.toSet())
      );
    }

    return response;
  }

  public static UserSummaryResponse toSummaryResponse(User user) {
    UserSummaryResponse response = new UserSummaryResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setFullName(user.getFullName());
    return response;
  }

}
