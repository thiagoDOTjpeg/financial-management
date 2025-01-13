package br.com.gritti.app.service;

import br.com.gritti.app.model.User;
import br.com.gritti.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

  private final Logger logger = Logger.getLogger(UserServices.class.getName());

  UserRepository repository;

  @Autowired
  public UserServices(UserRepository repository) {
    this.repository = repository;
  }

  public User createUser(User data) {
    var user = new User();
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
