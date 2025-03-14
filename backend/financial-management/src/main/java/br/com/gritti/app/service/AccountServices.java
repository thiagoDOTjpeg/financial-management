package br.com.gritti.app.service;

import br.com.gritti.app.data.dto.account.AccountRequestDTO;
import br.com.gritti.app.data.dto.account.AccountResponseDTO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.mapper.AccountMapper;
import br.com.gritti.app.mapper.UserMapper;
import br.com.gritti.app.model.Account;
import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.AccountRepository;
import br.com.gritti.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServices {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final AccountMapper accountMapper;


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

    UserMinVO vo = userMapper.userToUserMinVO(user);
    AccountResponseDTO responseDTO = new AccountResponseDTO(account);
    responseDTO.setUser(vo);

    return responseDTO;
  }

  public List<AccountResponseDTO> getAllAccounts() {
    List<Account> result = accountRepository.findAll();
    List<AccountResponseDTO> responseDTOs = result.stream().map(AccountResponseDTO::new).toList();
    return responseDTOs;
  }

}
