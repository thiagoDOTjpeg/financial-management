package br.com.gritti.app.shared.util.hateoas;

import br.com.gritti.app.application.dto.user.UserAssignRoleDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.dto.user.UserUpdateDTO;
import br.com.gritti.app.application.dto.minimal.UserMinimalDTO;
import br.com.gritti.app.domain.enums.AccountStatus;
import br.com.gritti.app.interfaces.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserHateoasUtil {
  public static void addLinks(UserResponseDTO userResponseDTO) {
    userResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(userResponseDTO.getId())).withSelfRel().withType("GET"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).updateUser(userResponseDTO.getId(), new UserUpdateDTO())).withRel("update").withType("PUT"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).deleteUser(userResponseDTO.getId())).withRel("deleteUser").withType("DELETE"));
    userResponseDTO.add(linkTo(methodOn(UserController.class).assignRoleToUser(new UserAssignRoleDTO(""), userResponseDTO.getId())).withRel("assignRole").withType("POST"));
    if(userResponseDTO.getAccountStatus() == AccountStatus.ACTIVE) {
      userResponseDTO.add(linkTo(methodOn(UserController.class).inactivateUser(userResponseDTO.getId())).withRel("inactivateUser").withType("PATCH"));
    }
  }

  public static void addLinkGetMinimalVO(UserMinimalDTO userMinimalDTO) {
    userMinimalDTO.add(linkTo(methodOn(UserController.class).getUserById(userMinimalDTO.getId())).withSelfRel().withType("GET"));
  }
}
