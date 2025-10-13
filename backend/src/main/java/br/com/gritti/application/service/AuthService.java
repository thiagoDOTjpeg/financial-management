package br.com.gritti.application.service;

import br.com.gritti.domain.vo.AccountCredentials;
import br.com.gritti.domain.vo.Token;

public interface AuthService {
   Token signin(AccountCredentials data);
   Token refreshToken(String username, String refreshToken);
}
