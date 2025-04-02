package br.com.gritti.app.infra.security.jwt;

import br.com.gritti.app.domain.valueobject.Token;
import br.com.gritti.app.shared.exceptions.InvalidJWTAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenProvider {

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length}")
  private long validityInMilliseconds;

  @Autowired
  private UserDetailsService userDetailsService;

  Algorithm algorithm = null;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey);
  }

  public Token createToken(String username, List<String> permissions) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    var accessToken = createAccessToken(username, permissions, now, validity);
    var refreshToken = createRefreshToken(username, permissions, now);
    return new Token(username, true, now, validity, accessToken, refreshToken);
  }

  public Token refreshToken(String refreshToken) {
    if(refreshToken.contains("Bearer "))
      refreshToken = refreshToken.substring("Bearer ".length());

      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(refreshToken);
      String username = jwt.getSubject();
      List<String> permissions = jwt.getClaim("permissions").asList(String.class);
    return createToken(username, permissions);
  }

  private String createAccessToken(String username, List<String> permissions, Date now, Date validity) {
    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return JWT.create()
            .withClaim("permissions", permissions)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerUrl)
            .sign(algorithm)
            .strip();
  }

  private String createRefreshToken(String username, List<String> permissions, Date now) {
    Date validityRefreshToken = new Date(now.getTime() + validityInMilliseconds * 3);
    return JWT.create()
            .withClaim("permissions", permissions)
            .withIssuedAt(now)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .strip();
  }

  public Authentication getAuthentication(String token) {
    DecodedJWT decodedJWT = decodeToken(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private DecodedJWT decodeToken(String token) {
    var algorithm = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt;
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
        throw new InvalidJWTAuthenticationException("Expired JWT token");
      }
      return true;
    } catch (Exception e) {
      if(e instanceof InvalidJWTAuthenticationException) {
        throw e;
      }
      throw new InvalidJWTAuthenticationException("Expired or Invalid token!!");
    }
  }
}
