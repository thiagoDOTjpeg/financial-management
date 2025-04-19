package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.dto.UserUpdateDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.interfaces.controller.UserController;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    log.info("APPLICATION: Received request in application and passing to domain to get all users");

    List<UserResponseDTO> usersDTOs = userDomainService.getUsers().stream().map(userMapper::userToUserResponseDTOPermissionCheck).toList();
    usersDTOs.forEach(u -> {
      u.add(linkTo(methodOn(UserController.class).getUserById(u.getId())).withSelfRel().withType("GET"));
      u.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users").withType("GET"));
    });
    return usersDTOs;
  }

  public UserResponseDTO getUserById(UUID id) {
      log.info("APPLICATION: Received request in application and passing to domain to find a user by id: {}", id);
      
      User user = userDomainService.getUserById(id);
      UserResponseDTO userDTO = userMapper.userToUserResponseDTOPermissionCheck(user);
      userDTO.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel().withType("GET"));
      userDTO.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users").withType("GET"));
      return userDTO;
  }

  public UserResponseDTO createUser(UserCreateDTO userDTO) {
    log.info("APPLICATION: Received request in application and passing to domain to create a new user: {}", userDTO);

    userDomainService.validateUsernameEmail(userDTO.getEmail(), userDTO.getUsername());
    User user = userMapper.userCreateDTOtoUser(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    User userCreated = userDomainService.createUser(user);
    return userMapper.userToUserResponseDTOPermissionCheck(userCreated);
  }

  public UserResponseDTO updateUser(UUID id, UserUpdateDTO userDTO) {
    log.info("APPLICATION: received request in application and passing to domain to update a user: {}", id);
    User user = userMapper.userUpdateDTOtoUser(userDTO);
    User updatedUser = userDomainService.updateUser(id, user);
    return userMapper.userToUserResponseDTOPermissionCheck(updatedUser);
  }

  public void deleteUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to delete a user: {}", id);
    userDomainService.deleteUser(id);
  }

  public UserResponseDTO inactivateUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to inactivate user: {}", id);
    return userMapper.userToUserResponseDTOPermissionCheck(userDomainService.inactivateUser(id));
  }

  public UserResponseDTO assignRoleToUser(UUID userId, String roleName) {
    log.info("APPLICATION: Received request in application and passing to domain to assign role {} to user", roleName);

    UserResponseDTO dto = userMapper.userToUserResponseDTOPermissionCheck(userDomainService.assignRoleToUser(userId, roleName));
    dto.add(linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel().withType("GET"));
    dto.add(linkTo(methodOn(UserController.class).getUsers()).withRel("users").withType("GET"));
    return dto;
  }
}
