package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.interfaces.controller.UserController;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.UUID;

@Service
public class UserApplicationService {
  private final Logger log = LoggerFactory.getLogger(UserApplicationService.class.getName());

  @Autowired
  private UserDomainService userDomainService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder encoder;


  public List<UserResponseDTO> getUsers() {
    log.info("Received request in application and passing to domain to get all users");

    List<UserResponseDTO> usersDTOs = userDomainService.getUsers().stream().map(userMapper::userToUserResponseDTOPermissionCheck).toList();
    usersDTOs.forEach(u -> {
      u.add(linkTo(methodOn(UserController.class).getUserById(u.getId())).withSelfRel().withType("GET"));
      u.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users").withType("GET"));
    });
    return usersDTOs;
  }

  public UserResponseDTO getUserById(UUID id) {
      log.info("Received request in application and passing to domain to a user by id: {}", id);
      
      User user = userDomainService.getUserById(id);
      UserResponseDTO userDTO = userMapper.userToUserResponseDTOPermissionCheck(user);
      userDTO.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel().withType("GET"));
      userDTO.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users").withType("GET"));
      return userDTO;
  }

  public UserResponseDTO createUser(UserCreateDTO userDTO) {
    log.info("Received request in application and passing to domain to create a new user: {}", userDTO);

    if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    userDomainService.validateUsernameEmail(userDTO.getEmail(), userDTO.getUsername());
    User user = userMapper.userCreateDTOtoUser(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    User userCreated = userDomainService.createUser(user);
    return userMapper.userToUserResponseDTOPermissionCheck(userCreated);
  }
}
