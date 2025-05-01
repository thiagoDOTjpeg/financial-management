package br.com.gritti.app.shared.util;

import br.com.gritti.app.application.dto.user.UserAssignRoleDTO;
import br.com.gritti.app.application.dto.user.UserCreateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.dto.user.UserUpdateDTO;
import br.com.gritti.app.domain.enums.AccountStatus;
import br.com.gritti.app.interfaces.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserHateoasUtil {
  public static void addLinks(UserResponseDTO userResponseDTO) {
    userResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(userResponseDTO.getId())).withSelfRel().withType("GET"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).getUsers(0, 12, "asc")).withRel("users").withType("GET"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).updateUser(userResponseDTO.getId(), new UserUpdateDTO())).withRel("update").withType("PUT"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).createUser(new UserCreateDTO())).withRel("create").withType("POST"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).deleteUser(userResponseDTO.getId())).withRel("delete-user").withType("DELETE"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).assignRoleToUser(new UserAssignRoleDTO(""), userResponseDTO.getId())).withRel("assign-role").withType("POST"));
    if(userResponseDTO.getAccountStatus() == AccountStatus.ACTIVE) {
      userResponseDTO.add(linkTo(methodOn(UserController.class).inactivateUser(userResponseDTO.getId())).withRel("inactivate-user").withType("PATCH"));
    }
  }
}
