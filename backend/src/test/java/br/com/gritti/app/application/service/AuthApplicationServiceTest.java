package br.com.gritti.app.application.service;

import br.com.gritti.app.domain.service.AuthDomainService;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.factory.AuthTestFactory;
import br.com.gritti.app.factory.TokenProviderTestFactory;
import br.com.gritti.app.infra.security.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AuthApplicationServiceTest {

  @Mock
  private AuthApplicationService service;

  @Mock
  private AuthDomainService domainService;

  @Mock
  private TokenProvider token;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
  }

  @Test
  void refreshTokenShouldReturnTokenWhenValid() {
    Token expectedToken = TokenProviderTestFactory.createToken();
    when(domainService.refreshToken(anyString(), anyString())).thenReturn(expectedToken);

    Token result = service.refreshToken("username", "refresh-token");

    assertEquals(expectedToken, result);
    verify(domainService).refreshToken("username", "refresh-token");

  }
}