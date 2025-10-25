package br.com.gritti.infra.security.jwt;

import br.com.gritti.domain.model.User;
import br.com.gritti.domain.repository.UserRepository;
import br.com.gritti.domain.vo.Token;
import br.com.gritti.shared.exception.InvalidJWTAuthenticationException;
import br.com.gritti.shared.exception.ResourceNotFoundException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenProvider {
  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length}")
  private long validityInMilliseconds;

  private final UserRepository userRepository;

  @Autowired
  public TokenProvider(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  Algorithm algorithm = null;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey);
  }

  public Token createToken(User user) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    var accessToken = createAccessToken(user.getUsername(), user.getPermissions(), now, validity, user.getId().toString());
    var refreshToken = createRefreshToken(user.getUsername(), user.getPermissions(), now, user.getId().toString());
    return new Token(user.getUsername(), true, now, validity, accessToken, refreshToken);
  }

  public Token refreshToken(String refreshToken) {
    if(!refreshToken.startsWith("Bearer ")) throw new InvalidJWTAuthenticationException("Refresh token inválido");
    refreshToken = refreshToken.substring("Bearer ".length());

    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT jwt = verifier.verify(refreshToken);
    String username = jwt.getSubject();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);
    List<String> permissions = jwt.getClaim("permissions").asList(String.class);
    String userId = jwt.getClaim("userId").asString();

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    String accessToken = createAccessToken(username, permissions, now, validity, userId);

    return new Token(username, true, now, validity, accessToken, refreshToken);
  }

  private String createAccessToken(String username, List<String> permissions, Date now, Date validity, String userId) {
    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return JWT.create()
            .withClaim("permissions", permissions)
            .withClaim("userId", userId)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerUrl)
            .sign(algorithm)
            .strip();
  }

  private String createRefreshToken(String username, List<String> permissions, Date now, String userId) {
    Date validityRefreshToken = new Date(now.getTime() + validityInMilliseconds * 3);
    return JWT.create()
            .withClaim("permissions", permissions)
            .withClaim("userId", userId)
            .withIssuedAt(now)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .strip();
  }

  public Authentication getAuthentication(String token) {
    DecodedJWT decodedJWT = decodeToken(token);

    List<String> permissions = decodedJWT.getClaim("permissions").asList(String.class);

    List<GrantedAuthority> authorities = permissions.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(decodedJWT, "", authorities);
  }

  private DecodedJWT decodeToken(String token) {
    var algorithm = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    return verifier.verify(token);
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }

  public Boolean validateToken(String token) {
    try {
      DecodedJWT decodedJWT = decodeToken(token);
      if(decodedJWT.getExpiresAt().before(new Date())) {
        throw new InvalidJWTAuthenticationException("Token de acesso expirou");
      }
      return true;
    } catch (Exception e) {
      if(e instanceof InvalidJWTAuthenticationException) {
        throw e;
      }
      throw new InvalidJWTAuthenticationException("Token expirado ou inválido!!");
    }
  }
}
