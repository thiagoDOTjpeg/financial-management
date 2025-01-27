package br.com.gritti.app.service;

import br.com.gritti.app.data.dto.user.UserRequestDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.mapper.UserMapper;
import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.UserRepository;
import br.com.gritti.app.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

  private final Logger logger = Logger.getLogger(UserServices.class.getName());
  private final UserRepository repository;
  private final UserMapper mapper;
  private final PasswordEncoderUtil encoder;

  @Autowired
  public UserServices(UserRepository repository, PasswordEncoderUtil encoder, UserMapper mapper) {
    this.repository = repository;
    this.encoder = encoder;
    this.mapper = mapper;
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }

  public User getUserById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("ID " + id + " not found"));
  }


  public UserResponseDTO createUser(UserRequestDTO data) {
    User user = mapper.requestToUser(data);
    user.setPassword(encoder.encodePassword(data.getPassword()));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setAccountStatus(true);

    User savedUser = repository.save(user);

    return mapper.userToResponseDTO(savedUser);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Finding one user by name " + username + "!");
    var user = repository.findByUsername(username);
    if(user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found!");
    }
  }
}
