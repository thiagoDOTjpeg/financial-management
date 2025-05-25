package br.com.gritti.app.shared.util;

import br.com.gritti.app.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
  public static boolean isAdmin() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
  }

  public static String getCurrentUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? auth.getName() : null;
  }

  public static User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? (User) auth.getPrincipal() : null;
  }
}
