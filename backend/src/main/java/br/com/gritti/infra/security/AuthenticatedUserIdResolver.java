package br.com.gritti.infra.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class AuthenticatedUserIdResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(AuthenticatedUserId.class) &&
            parameter.getParameterType().equals(UUID.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (principal instanceof DecodedJWT jwt) {
      String userIdStr = jwt.getClaim("userId").asString();
      if (userIdStr != null) {
        return UUID.fromString(userIdStr);
      }
    }
    throw new IllegalStateException("Não foi possível resolver o ID do usuário a partir do principal de autenticação.");
  }
}
