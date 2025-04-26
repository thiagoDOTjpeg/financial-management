package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.Email;
import br.com.gritti.app.factory.UserTestFactory;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.shared.exceptions.EmailAlreadyExistsException;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import br.com.gritti.app.shared.exceptions.UsernameAlreadyExistsException;
import br.com.gritti.app.shared.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class UserDomainServiceTest {

  @InjectMocks
  private UserDomainService service;

  @Mock
  private UserRepositoryImpl repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUsers() {
    List<User> users = UserTestFactory.createUserList(2);

    when(repository.findAll()).thenReturn(users);

    List<User> resultUsers = service.getUsers();

    assertEquals(users, resultUsers);
    assertEquals(users.size(), resultUsers.size());
    assertNotNull(resultUsers.get(0));

    verify(repository, times(1)).findAll();

  }
  @Test
  void getUserById() {
    User user = UserTestFactory.createUser(1);

    when(repository.findById(user.getId())).thenReturn(Optional.of(user));

    User resultUser = service.getUserById(user.getId());

    assertNotNull(resultUser);
    assertEquals(user, resultUser);

    verify(repository, times(1)).findById(user.getId());
  }

  @Test
  void shouldThrowUserNotFoundException() {
    UUID userId = UUID.randomUUID();
    when(repository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> service.getUserById(userId));
  }

  @Test
  void createUser() {
    User user = UserTestFactory.createUser(1);

    User createdUser = service.createUser(user);

    assertNotNull(createdUser);
    assertEquals(user.getId(), createdUser.getId());

    verify(repository, times(1)).save(user);
  }

  @Test
  void loadUserByUsername() {
    User user = UserTestFactory.createUser(1);
    String username = UserTestFactory.createUser(1).getUsername();

    when(repository.findByUsername(username)).thenReturn(user);

    User resultUser = service.loadUserByUsername(username);

    assertNotNull(resultUser);
    assertEquals(user.getId(), resultUser.getId());
    assertEquals(username, resultUser.getUsername());

    verify(repository, times(1)).findByUsername(username);
  }

  @Test
  void loadUserByUsernameNotFound() {
    String username = "testuser1";

    assertThrows(UsernameNotFoundException.class, () ->
            service.loadUserByUsername(username));

  }

  @Test
  void shouldThrowEmailAlreadyExistsException() {
    Email email = new Email("test@example.com");
    String username = "testuser1";

    when(repository.existsByEmail(email)).thenReturn(true);
    when(repository.existsByUsername(username)).thenReturn(false);

    assertThrows(EmailAlreadyExistsException.class, () -> service.validateUsernameEmail(email.getValue(), username));

    verify(repository, times(1)).existsByEmail(email);
    verify(repository, never()).existsByUsername(username);
  }

  @Test
  void shouldThrowUsernameAlreadyExistsException() {
    Email email = new Email("test@example.com");
    String username = "testuser1";
    when(repository.existsByEmail(email)).thenReturn(false);
    when(repository.existsByUsername(username)).thenReturn(true);

    assertThrows(UsernameAlreadyExistsException.class, () -> service.validateUsernameEmail(email.getValue(), username));

    verify(repository, times(1)).existsByEmail(email);
    verify(repository, times(1)).existsByUsername(username);
  }
}