package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.Role;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.factory.UserTestFactory;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.infra.security.jwt.TokenProvider;
import br.com.gritti.app.shared.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AuthDomainServiceTest {

  @InjectMocks
  private AuthDomainService service;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenProvider tokenProvider;

  @Mock
  private UserRepositoryImpl repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void signin() {
    String username = "testuser";
    String password = "hashedPassword";
    AccountCredentials credentials = new AccountCredentials(username, password);

    User mockUser = new User();
    mockUser.setUsername(username);
    mockUser.setPassword(password);
    mockUser.setRoles(new Role(UUID.randomUUID(),"ROLE_USER"));

    Token expectedToken = new Token(username, true,
            new Date(),
            new Date(System.currentTimeMillis() + 1000 * 60 * 10), "access-token-123", "refresh-token-abc");

    when(repository.findByUsername(username)).thenReturn(mockUser);
    when(tokenProvider.createToken(username, mockUser.getPermissions())).thenReturn(expectedToken);

    Token result = service.signin(credentials);

    assertNotNull(result);
    assertEquals(expectedToken.getAccessToken(), result.getAccessToken());
    assertEquals(expectedToken.getRefreshToken(), result.getRefreshToken());
    assertEquals(expectedToken.getUsername(), result.getUsername());
    assertTrue(result.getAuthenticated());
    assertThat(mockUser.getLastLogin()).isCloseTo(new Date(), 1000);

    verify(repository, times(1)).findByUsername(username);
    verify(tokenProvider, times(1)).createToken(username, mockUser.getPermissions());

  }

  @Test
  void signinShouldThrowBadCredentialsException() {
    String username = "testuser";
    String password = "hashedPassword";
    AccountCredentials credentials = new AccountCredentials(username, password);
    User mockUser = new User();
    mockUser.setUsername(username);
    mockUser.setPassword(password);
    mockUser.setRoles(new Role(UUID.randomUUID(),"ROLE_USER"));

    when(repository.findByUsername(username)).thenReturn(null);

    assertThrows(BadCredentialsException.class, () -> service.signin(credentials));

    verify(repository, times(1)).findByUsername(username);
    verify(tokenProvider, never()).createToken(username, mockUser.getPermissions());
  }

  @Test
  void refreshToken() {
    String username = "testuser";
    String refreshToken = "refresh-token-abc";
    User mockUser = UserTestFactory.createUser();
    Token expectedToken = new Token(username, true,
            new Date(),
            new Date(System.currentTimeMillis() + 1000 * 60 * 10), "access-token-321",
            "refresh-token-abc");


    when(repository.findByUsername(username)).thenReturn(mockUser);
    when(tokenProvider.refreshToken(refreshToken)).thenReturn(expectedToken);

    Token refreshedToken = service.refreshToken(username, refreshToken);

    assertNotNull(refreshedToken);
    assertEquals(expectedToken.getAccessToken(), refreshedToken.getAccessToken());
    assertEquals(expectedToken.getRefreshToken(), refreshedToken.getRefreshToken());
    assertEquals(expectedToken.getUsername(), refreshedToken.getUsername());
    assertTrue(refreshedToken.getAuthenticated());
    assertThat(mockUser.getLastLogin()).isCloseTo(new Date(), 1000);

    verify(repository, times(1)).findByUsername(username);
    verify(tokenProvider, times(1)).refreshToken(refreshToken);
  }

  @Test
  void refreshTokenShouldThrowUsernameNotFoundException() {
    String username = "testuser";
    String password = "hashedPassword";
    AccountCredentials credentials = new AccountCredentials(username, password);
    User mockUser = new User();
    mockUser.setUsername(username);
    mockUser.setPassword(password);
    mockUser.setRoles(new Role(UUID.randomUUID(),"ROLE_USER"));
    Token token = new Token(username, true,
            new Date(),
            new Date(System.currentTimeMillis() + 1000 * 60 * 10),
            "access-token-321", "refresh-token-abc");


    when(repository.findByUsername(username)).thenReturn(null);

    assertThrows(UsernameNotFoundException.class, () -> service.refreshToken(username, token.getRefreshToken()));

    verify(repository, times(1)).findByUsername(username);
    verify(tokenProvider, never()).createToken(username, mockUser.getPermissions());
  }

  @Test
  void shouldRefreshTokenFromAuthenticatedUser() {
    String username = "testuser";
    String password = "hashedPassword";
    User mockUser = new User();
    mockUser.setUsername(username);
    mockUser.setPassword(password);
    mockUser.setRoles(new Role(UUID.randomUUID(),"ROLE_USER"));

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    SecurityContext context = mock(SecurityContext.class);
    SecurityContextHolder.setContext(context);

    when(repository.findByUsername(username)).thenReturn(mockUser);

    Token newToken = new Token(username, true, new Date(), new Date(System.currentTimeMillis() + 1000 * 60 * 10),
            "access-token", "refresh-token");

    when(tokenProvider.refreshToken("existing-refresh-token")).thenReturn(newToken);

    String existingUsername = authenticationToken.getName();
    String existingRefreshToken = "existing-refresh-token";

    Token result = service.refreshToken(existingUsername, existingRefreshToken);

    assertEquals("access-token", result.getAccessToken());
    assertEquals("refresh-token", result.getRefreshToken());

    verify(repository).save(mockUser);
  }
}