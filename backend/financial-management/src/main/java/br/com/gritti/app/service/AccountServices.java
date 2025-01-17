package br.com.gritti.app.service;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.data.vo.UserVO;
import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.AccountRepository;
import br.com.gritti.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServices {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  @Autowired
  public AccountServices(AccountRepository accountRepository, UserRepository userRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
  }

  public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) throws Exception {

  }

}
