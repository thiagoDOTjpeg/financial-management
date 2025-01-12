package br.com.gritti.app.security.jwt;

import br.com.gritti.app.data.security.TokenVO;
import br.com.gritti.app.exception.InvalidJWTAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key}")
  private String secret;

  @Value("${security.jwt.token.expire-length}")
  private long expiration;

  @Autowired
  private UserDetailsService userDetailsService;

  Algorithm algorithm = null;

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
    algorithm = Algorithm.HMAC256(secret.getBytes());
  }

  public TokenVO createAccessToken(String username, List<String> roles) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);
    var accessToken = getAccessToken(username, roles, now, expiryDate);
    var refreshToken = getRefreshToken(username, roles, now);
    return new TokenVO(username, true, now, expiryDate, accessToken, refreshToken);
  }

  public TokenVO refreshToken(String refreshToken) {
    if(refreshToken.contains("Bearer "))
      refreshToken = refreshToken.substring("Bearer ".length());

    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT jwt = verifier.verify(refreshToken);
    String username = jwt.getSubject();
    List<String> roles = jwt.getClaim("roles").asList(String.class);
    return createAccessToken(username, roles);
  }

  public String getAccessToken(String username, List<String> roles, Date now, Date expiryDate) {
    String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(expiryDate).withIssuer(issueUrl).withSubject(username).sign(algorithm).strip();
  }

  public String getRefreshToken(String username, List<String> roles, Date now) {
    Date expiryDate = new Date(now.getTime() + (expiration * 3));
    return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(expiryDate).withSubject(username).sign(algorithm).strip();
  }
  public Authentication getAuthentication(String token) {
    DecodedJWT decodedJWT = decodedToken(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private DecodedJWT decodedToken(String token) {
    var alg = Algorithm.HMAC256(secret.getBytes());
    JWTVerifier verifier = JWT.require(alg).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }

  public Boolean validateToken(String token) {
    DecodedJWT decodedJWT = decodedToken(token);
    try {
      if(decodedJWT.getExpiresAt().before(new Date())) {
        return false;
      }
      return true;
    } catch (Exception e) {
      throw new InvalidJWTAuthenticationException("Expired or invalid JWT token");
    }
  }
}
