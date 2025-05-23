package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.user.UserCreateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.factory.UserTestFactory;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.interfaces.controller.UserController;
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

import java.util.UUID;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

  @InjectMocks
  private UserApplicationService service;

  @Mock
  private UserController controller;

  @Mock
  private UserRepositoryImpl repository;

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
    /*
    List<User> users = UserTestFactory.createUserList(2);
    List<UserResponseDTO> usersDTOs = UserTestFactory.createUserResponseDTOList(2);

    when(domainService.getUsers()).thenReturn(users);
    when(mapper.userToUserResponseDTOPermissionCheck(users.get(0))).thenReturn(usersDTOs.get(0));
    when(mapper.userToUserResponseDTOPermissionCheck(users.get(1))).thenReturn(usersDTOs.get(1));

    CollectionModel<UserResponseDTO> userResponseDTOs = service.getUsers();

    assertEquals(2, userResponseDTOs.getContent().size());
    assertEquals(usersDTOs.get(0), userResponseDTOs.iterator().next());

    boolean hasSelfLink = false;
    boolean hasUsersLink = false;

    for(Link link : userResponseDTOs.getContent().iterator().next().getLinks()) {
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
  */
  }

  @Test
  void getUserById() {
    User user = UserTestFactory.createUser(1);
    UUID userId = user.getId();
    UserResponseDTO userResponseDTO = UserTestFactory.createUserResponseDTO(user);


    when(domainService.getUserById(user.getId())).thenReturn(user);
    when(mapper.userToUserResponseDTO(user)).thenReturn(userResponseDTO);

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
    verify(mapper, times(1)).userToUserResponseDTO(user);
  }

  @Test
  void createUser() {
    UserCreateDTO userCreateDTO = UserTestFactory.createUserCreateDTO();
    User user = UserTestFactory.createUser();
    UserResponseDTO userResponseDTO = UserTestFactory.createUserResponseDTO(user);

    when(mapper.userCreateDTOtoUser(userCreateDTO)).thenReturn(user);
    when(passwordEncoder.encode(userCreateDTO.getPassword())).thenReturn("hashedPassword");
    when(domainService.createUser(user)).thenReturn(user);
    when(mapper.userToUserResponseDTO(user)).thenReturn(userResponseDTO);

    UserResponseDTO responseDTO = service.createUser(userCreateDTO);

    assertNotNull(responseDTO);
    assertEquals(user.getId(), responseDTO.getId());
    assertEquals("testuser", responseDTO.getUsername());

    //verify(domainService, times(1)).validateUsernameEmail(new Email(userCreateDTO.getEmail()), userCreateDTO.getUsername());
    verify(mapper, times(1)).userCreateDTOtoUser(userCreateDTO);
    verify(passwordEncoder, times(1)).encode(userCreateDTO.getPassword());
    verify(domainService, times(1)).createUser(user);
    verify(mapper, times(1)).userToUserResponseDTO(user);
  }
}