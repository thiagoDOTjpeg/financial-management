package br.com.gritti.app.service;

import br.com.gritti.app.data.dto.user.UserCreateDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.UserRepository;
import br.com.gritti.app.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

  private final Logger logger = Logger.getLogger(UserServices.class.getName());

  UserRepository repository;

  private final PasswordEncoderUtil encoder;

  @Autowired
  public UserServices(UserRepository repository, PasswordEncoderUtil encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  public UserResponseDTO createUser(UserCreateDTO data) {
    Date now = new Date();
    var user = new User();
    user.setUsername(data.getUsername());
    user.setPassword(encoder.encodePassword(data.getPassword()));
    user.setEmail(data.getEmail());
    user.setFullName(data.getFullName());
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setAccountNonExpired(true);
    user.setAccountStatus(true);
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    User savedUser = repository.save(user);

    return new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), "User created successfully");
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
