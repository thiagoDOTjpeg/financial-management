package br.com.gritti.app.service;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.data.vo.UserVO;
import br.com.gritti.app.mapper.AccountMapper;
import br.com.gritti.app.mapper.UserMapper;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.AccountRepository;
import br.com.gritti.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServices {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final AccountMapper accountMapper;
  private final UserMapper userMapper;

  @Autowired
  public AccountServices(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper, UserMapper userMapper) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
    this.accountMapper = accountMapper;
    this.userMapper = userMapper;
  }

  public AccountResponseDTO createAccount(AccountRequestDTO dto) throws Exception {
    Account account = accountMapper.requestToAccount(dto);
    User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new Exception("User not found"));

    account.setUser(user);
    accountRepository.save(account);

    UserVO vo = userMapper.userToUserVO(user);
    AccountResponseDTO responseDTO = accountMapper.accountToResponseDTO(account);
    responseDTO.setUser(vo);

    return responseDTO;
  }

  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

}
