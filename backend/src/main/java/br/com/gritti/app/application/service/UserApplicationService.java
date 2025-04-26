package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.UserCreateDTO;
import br.com.gritti.app.application.dto.UserResponseDTO;
import br.com.gritti.app.application.dto.UserUpdateDTO;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.shared.util.UserHateoasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.util.Collection;
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


  public CollectionModel<UserResponseDTO> getUsers() {
    log.info("APPLICATION: Received request in application and passing to domain to get all users");
    return CollectionModel.of(userDomainService.getUsers().stream().map(userMapper::userToUserResponseDTOPermissionCheck).peek(UserHateoasUtil::addLinks).toList());
  }

  public UserResponseDTO getUserById(UUID id) {
      log.info("APPLICATION: Received request in application and passing to domain to find a user by id");
      
      User user = userDomainService.getUserById(id);
      UserResponseDTO userDTO = userMapper.userToUserResponseDTOPermissionCheck(user);
      UserHateoasUtil.addLinks(userDTO);
      return userDTO;
  }

  public UserResponseDTO createUser(UserCreateDTO userDTO) {
    log.info("APPLICATION: Received request in application and passing to domain to create a new user");

    userDomainService.validateUsernameEmail(userDTO.getEmail(), userDTO.getUsername());
    User user = userMapper.userCreateDTOtoUser(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    User userCreated = userDomainService.createUser(user);
    return userMapper.userToUserResponseDTOPermissionCheck(userCreated);
  }

  public UserResponseDTO updateUser(UUID id, UserUpdateDTO userDTO) {
    log.info("APPLICATION: received request in application and passing to domain to update a user");
    User user = userMapper.userUpdateDTOtoUser(userDTO);
    User updatedUser = userDomainService.updateUser(id, user);
    return userMapper.userToUserResponseDTOPermissionCheck(updatedUser);
  }

  public void deleteUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to delete a user");
    userDomainService.deleteUser(id);
  }

  public UserResponseDTO inactivateUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to inactivate user");
    return userMapper.userToUserResponseDTOPermissionCheck(userDomainService.inactivateUser(id));
  }

  public UserResponseDTO assignRoleToUser(UUID userId, String roleName) {
    log.info("APPLICATION: Received request in application and passing to domain to assign role to user");

    UserResponseDTO dto = userMapper.userToUserResponseDTOPermissionCheck(userDomainService.assignRoleToUser(userId, roleName));
    UserHateoasUtil.addLinks(dto);
    return dto;
  }
}
