package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.infra.security.jwt.TokenProvider;
import br.com.gritti.app.shared.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthDomainService {
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;
  private final UserRepositoryImpl userRepositoryImpl;

  @Autowired
  public AuthDomainService(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserRepositoryImpl userRepositoryImpl) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userRepositoryImpl = userRepositoryImpl;
  }

  public Token signin(AccountCredentials data){
    try {
      var username = data.getUsername();
      var password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      var user = userRepositoryImpl.findByUsername(username);

      Token tokenResponse = new Token();
      if(user != null) {
        tokenResponse = tokenProvider.createToken(username, user.getPermissions());
      } else {
        throw new UsernameNotFoundException("Username " + username + " not found");
      }
      user.setLastLogin(new Date());
      userRepositoryImpl.save(user);
      return tokenResponse;
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid username/password supplied!!");
    }
  }

  public Token refreshToken(String username, String refreshToken) {
    User user = userRepositoryImpl.findByUsername(username);

    Token tokenResponse;
    if(user != null) {
      tokenResponse = tokenProvider.refreshToken(refreshToken);
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found");
    }
    user.setLastLogin(new Date());
    userRepositoryImpl.save(user);
    return tokenResponse;
  }

}
