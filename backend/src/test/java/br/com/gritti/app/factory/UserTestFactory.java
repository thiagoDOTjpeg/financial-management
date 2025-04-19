package br.com.gritti.app.factory;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserTestFactory {

  public static User createUser() {
    return createUser(UUID.randomUUID(), "testuser", "test@example.com");
  }

  public static User createUser(int index) {
    return createUser(
            UUID.randomUUID(),
            "testuser" + index,
            "test" + index + "@example.com"
    );
  }

  public static User createUser(UUID id, String username, String email) {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setEmail(new Email(email));
    user.setPassword("hashedPassword");
    user.setLastLogin(new Date());
    user.setRoles(new Role());
    return user;
  }

  public static UserResponseDTO createUserResponseDTO(User user) {
    UserResponseDTO dto = new UserResponseDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setEmail(user.getEmail().getValue());
    dto.setCreatedBy("system");
    dto.setCreatedAt(LocalDateTime.now());
    dto.setUpdatedBy("system");
    dto.setUpdatedAt(LocalDateTime.now());
    return dto;
  }

  public static UserResponseDTO createUserResponseDTO() {
    User user = createUser();
    return createUserResponseDTO(user);
  }

  public static UserResponseDTO createUserResponseDTO(int index) {
    User user = createUser(index);
    return createUserResponseDTO(user);
  }

  public static UserResponseDTO createUserResponseDTO(UUID id, String username, String email) {
    UserResponseDTO dto = new UserResponseDTO();
    dto.setId(id);
    dto.setUsername(username);
    dto.setEmail(email);
    dto.setCreatedBy("system");
    dto.setCreatedAt(LocalDateTime.now());
    dto.setUpdatedBy("system");
    dto.setUpdatedAt(LocalDateTime.now());
    return dto;
  }

  public static UserCreateDTO createUserCreateDTO() {
    return createUserCreateDTO("testuser", "test@example.com", "password123");
  }

  public static UserCreateDTO createUserCreateDTO(String username, String email, String password) {
    UserCreateDTO dto = new UserCreateDTO();
    dto.setUsername(username);
    dto.setEmail(email);
    dto.setPassword(password);
    return dto;
  }

  public static List<User> createUserList(int size) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      users.add(createUser(i));
    }
    return users;
  }

  public static List<UserResponseDTO> createUserResponseDTOList(int size) {
    List<UserResponseDTO> dtos = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      dtos.add(createUserResponseDTO(i));
    }
    return dtos;
  }
}
