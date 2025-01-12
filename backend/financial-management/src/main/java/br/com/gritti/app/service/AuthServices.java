package br.com.gritti.app.service;

import br.com.gritti.app.data.security.AccountCredentialsVO;
import br.com.gritti.app.data.security.TokenVO;
import br.com.gritti.app.repository.UserRepository;
import br.com.gritti.app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServices {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UserRepository userRepository;

  @Autowired
  public AuthServices(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userRepository = userRepository;
  }

  @SuppressWarnings("rawtypes")
  public ResponseEntity signin(AccountCredentialsVO accountCredentials) {
    try {
      var username = accountCredentials.getUserName();
      var password = accountCredentials.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      var user = userRepository.findByUsername(username);

      var tokenResponse = new TokenVO();
      if (user != null) {
        tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
      } else {
        throw new UsernameNotFoundException("username " + username + " not found!");
      }
      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid username or password!");
    }
  }

  @SuppressWarnings("rawtypes")
  public ResponseEntity refreshToken(String username, String refreshToken) {
    var user = userRepository.findByUsername(username);

    var tokenResponse = new TokenVO();
    if (user != null) {
      tokenResponse = tokenProvider.refreshToken(refreshToken);
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found!");
    }
    return ResponseEntity.ok(tokenResponse);
  }
}
