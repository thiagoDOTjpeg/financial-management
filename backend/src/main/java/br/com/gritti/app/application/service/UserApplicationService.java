package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserApplicationService {
  private final Logger log = LoggerFactory.getLogger(UserApplicationService.class.getName());
  private final UserDomainService userDomainService;
  private final UserMapper userMapper;
  private final PasswordEncoder encoder;

  @Autowired
  public UserApplicationService(UserDomainService userDomainService, UserMapper userMapper, PasswordEncoder encoder) {
    this.userDomainService = userDomainService;
    this.userMapper = userMapper;
    this.encoder = encoder;
  }

  public List<User> getUsers() {
    return userDomainService.getUsers();
  }

  public Optional<User> getUserById(UUID id) {
    return userDomainService.getUserById(id);
  }

  public User createUser(UserCreateDTO userDTO) {
    if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    userDomainService.validateUsernameEmail(userDTO.getEmail(), userDTO.getUsername());
    User user = userMapper.toUser(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    return userDomainService.createUser(user);
  }

}
