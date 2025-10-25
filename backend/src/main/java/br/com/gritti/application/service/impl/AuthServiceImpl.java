package br.com.gritti.application.service.impl;

import br.com.gritti.application.service.AuthService;
import br.com.gritti.domain.enums.AccountStatus;
import br.com.gritti.domain.model.User;
import br.com.gritti.domain.repository.UserRepository;
import br.com.gritti.domain.vo.AccountCredentials;
import br.com.gritti.domain.vo.Token;
import br.com.gritti.infra.security.jwt.TokenProvider;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import br.com.gritti.shared.exception.UserIsInactiveException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;

  @Autowired
  public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userRepository = userRepository;
  }

  @Override
  public Token signin(AccountCredentials data) {
    String username = data.username();
    String password = data.password();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    if(user.getAccountStatus().equals(AccountStatus.INACTIVE)) throw new UserIsInactiveException("O Usuário está inativo");

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      Token tokenResponse;
      tokenResponse = tokenProvider.createToken(user);
      user.setLastLogin(LocalDateTime.now());
      userRepository.save(user);
      return tokenResponse;
    } catch (Exception e) {
      throw new BadCredentialsException("Usuário ou senha inválidos");
    }
  }

  @Override
  public Token refreshToken(String refreshToken) {
    try {
      Token tokenResponse;
      tokenResponse = tokenProvider.refreshToken(refreshToken);
      return tokenResponse;
    } catch (Exception e) {
      throw new BadCredentialsException("Refresh token inválido");
    }
  }
}
