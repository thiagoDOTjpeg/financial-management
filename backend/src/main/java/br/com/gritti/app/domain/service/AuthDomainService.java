package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.enums.AccountStatus;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.valueobject.AccountCredentials;
import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.infra.repository.UserRepositoryImpl;
import br.com.gritti.app.infra.security.jwt.TokenProvider;
import br.com.gritti.app.shared.exceptions.UserIsInactiveException;
import br.com.gritti.app.shared.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthDomainService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private UserRepositoryImpl userRepositoryImpl;

  public Token signin(AccountCredentials data){
    String username = data.getUsername();
    String password = data.getPassword();
    User user = userRepositoryImpl.findByUsername(username);
    if(user == null) throw new UsernameNotFoundException("Username not found");
    if(user.getAccountStatus() == AccountStatus.INACTIVE) throw new UserIsInactiveException("User is inactive");

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      Token tokenResponse;
      tokenResponse = tokenProvider.createToken(username, user.getPermissions());
      user.setLastLogin(new Date());
      userRepositoryImpl.save(user);
      return tokenResponse;
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid username/password supplied!!");
    }
  }

  public Token refreshToken(String username, String refreshToken) {
    User user = userRepositoryImpl.findByUsername(username);

    if(user == null) throw new UsernameNotFoundException("Username not found");
    
    Token tokenResponse;
    tokenResponse = tokenProvider.refreshToken(refreshToken);
    user.setLastLogin(new Date());
    userRepositoryImpl.save(user);
    return tokenResponse;
  }
}
