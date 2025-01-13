package br.com.gritti.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoderUtil {

  private final PasswordEncoder encoder;

  @Autowired
  public PasswordEncoderUtil(@Lazy PasswordEncoder encoder) {
    this.encoder = encoder;
  }

  public String encodePassword(String rawPassword) {
    if(rawPassword == null || rawPassword.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    return encoder.encode(rawPassword);
  }

  public boolean matches(String rawPassword, String encodedPassword) {
    if(rawPassword == null || rawPassword.isEmpty() || encodedPassword == null || encodedPassword.isEmpty()) {
      throw new IllegalArgumentException("Raw/Encoded Password cannot be null or empty");
    }
    return encoder.matches(rawPassword, encodedPassword);
  }
}
