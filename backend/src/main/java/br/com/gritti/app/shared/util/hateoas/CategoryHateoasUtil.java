package br.com.gritti.app.shared.util.hateoas;

import br.com.gritti.app.application.dto.category.CategoryCreateDTO;
import br.com.gritti.app.application.dto.category.CategoryResponseDTO;
import br.com.gritti.app.application.dto.category.CategoryUpdateDTO;
import br.com.gritti.app.interfaces.controller.BankAccountController;
import br.com.gritti.app.interfaces.controller.CategoryController;
import br.com.gritti.app.interfaces.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CategoryHateoasUtil {
  public static void addLinks(CategoryResponseDTO categoryResponseDTO) {
    categoryResponseDTO.add(linkTo(methodOn(CategoryController.class).getCategoryById(categoryResponseDTO.getId())).withSelfRel().withType("GET"));
    categoryResponseDTO.add(linkTo(methodOn(CategoryController.class).deleteCategory(categoryResponseDTO.getId())).withRel("delete").withType("DELETE"));
    categoryResponseDTO.add(linkTo(methodOn(CategoryController.class).updateCategory(categoryResponseDTO.getId(), new CategoryUpdateDTO())).withRel("update").withType("PUT"));
    categoryResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(categoryResponseDTO.getUser().getId())).withRel("user").withType("GET"));
  }
}
