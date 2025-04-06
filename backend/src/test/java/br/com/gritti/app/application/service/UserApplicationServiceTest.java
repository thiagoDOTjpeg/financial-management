package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.factory.UserTestFactory;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

  @InjectMocks
  private UserApplicationService service;

  @Mock
  private UserDomainService domainService;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  private UserMapper mapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUsers() {
    List<User> users = UserTestFactory.createUserList(2);
    List<UserResponseDTO> usersDTOs = UserTestFactory.createUserResponseDTOList(2);

    when(domainService.getUsers()).thenReturn(users);
    when(mapper.userToUserResponseDTOPermissionCheck(users.get(0))).thenReturn(usersDTOs.get(0));
    when(mapper.userToUserResponseDTOPermissionCheck(users.get(1))).thenReturn(usersDTOs.get(1));

    List<UserResponseDTO> userResponseDTOs = service.getUsers();

    assertEquals(2, userResponseDTOs.size());
    assertEquals(usersDTOs.get(0), userResponseDTOs.get(0));

    boolean hasSelfLink = false;
    boolean hasUsersLink = false;

    for(Link link : userResponseDTOs.get(0).getLinks()) {
      if(link.getRel().toString().equals("self")) {
        hasSelfLink = true;
      }
      if(link.getRel().toString().equals("users")) {
        hasUsersLink = true;
      }
    }

    assertTrue(hasSelfLink);
    assertTrue(hasUsersLink);

    verify(domainService, times(1)).getUsers();
    verify(mapper, times(2)).userToUserResponseDTOPermissionCheck(any(User.class));

  }

  @Test
  void getUserById() {
    User user = UserTestFactory.createUser(1);
    UUID userId = user.getId();
    UserResponseDTO userResponseDTO = UserTestFactory.createUserResponseDTO(user);


    when(domainService.getUserById(user.getId())).thenReturn(Optional.of(user));
    when(mapper.userToUserResponseDTOPermissionCheck(user)).thenReturn(userResponseDTO);

    UserResponseDTO responseDTO = service.getUserById(user.getId());

    assertEquals(userId, responseDTO.getId());
    assertEquals("testuser1", responseDTO.getUsername());
    assertEquals("test1@example.com", responseDTO.getEmail());

    boolean hasSelfLink = false;
    boolean hasUsersLink = false;

    for(Link link : responseDTO.getLinks()) {
      if(link.getRel().toString().equals("self")) {
        hasSelfLink = true;
      }
      if(link.getRel().toString().equals("users")) {
        hasUsersLink = true;
      }
    }

    assertTrue(hasSelfLink);
    assertTrue(hasUsersLink);

    verify(domainService, times(1)).getUserById(user.getId());
    verify(mapper, times(1)).userToUserResponseDTOPermissionCheck(user);
  }

  @Test
  void getUserByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(domainService.getUserById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> service.getUserById(id));

    verify(domainService, times(1)).getUserById(id);
    verify(mapper, never()).userToUserResponseDTOPermissionCheck(any(User.class));
  }

  @Test
  void createUser() {
    UserCreateDTO userCreateDTO = UserTestFactory.createUserCreateDTO();
    User user = UserTestFactory.createUser();
    UserResponseDTO userResponseDTO = UserTestFactory.createUserResponseDTO(user);

    when(mapper.userCreateDTOtoUser(userCreateDTO)).thenReturn(user);
    when(passwordEncoder.encode(userCreateDTO.getPassword())).thenReturn("hashedPassword");
    when(domainService.createUser(user)).thenReturn(user);
    when(mapper.userToUserResponseDTOPermissionCheck(user)).thenReturn(userResponseDTO);

    UserResponseDTO responseDTO = service.createUser(userCreateDTO);

    assertNotNull(responseDTO);
    assertEquals(user.getId(), responseDTO.getId());
    assertEquals("testuser", responseDTO.getUsername());

    verify(domainService, times(1)).validateUsernameEmail(userCreateDTO.getEmail(), userCreateDTO.getUsername());
    verify(mapper, times(1)).userCreateDTOtoUser(userCreateDTO);
    verify(passwordEncoder, times(1)).encode(userCreateDTO.getPassword());
    verify(domainService, times(1)).createUser(user);
    verify(mapper, times(1)).userToUserResponseDTOPermissionCheck(user);
  }

  @Test
  void createUserWithEmptyPassword() {
    UserCreateDTO userCreateDTO = UserTestFactory.createUserCreateDTO("username", "email@example.com", "");

    assertThrows(IllegalArgumentException.class, () -> service.createUser(userCreateDTO));

    verify(domainService, never()).validateUsernameEmail(anyString(), anyString());
    verify(mapper, never()).userCreateDTOtoUser(any());
  }

  @Test
  void createUserWithNullPassword() {
    UserCreateDTO userCreateDTO = UserTestFactory.createUserCreateDTO("username", "email@example.com", null);

    assertThrows(IllegalArgumentException.class, () -> service.createUser(userCreateDTO));

    verify(domainService, never()).validateUsernameEmail(anyString(), anyString());
    verify(mapper, never()).userCreateDTOtoUser(any());
  }
}