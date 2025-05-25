package br.com.gritti.app.interfaces.controller;

import br.com.gritti.app.application.dto.category.CategoryCreateDTO;
import br.com.gritti.app.application.dto.category.CategoryResponseDTO;
import br.com.gritti.app.application.dto.category.CategoryUpdateDTO;
import br.com.gritti.app.application.service.CategoryApplicationService;
import br.com.gritti.app.domain.model.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category", description = "Operações relacionadas as categorias(tipos de transações)")
public class CategoryController {
  private final Logger log = LoggerFactory.getLogger(CategoryController.class);

  private final CategoryApplicationService categoryApplicationService;

  @Autowired
  public CategoryController(CategoryApplicationService categoryApplicationService) {
    this.categoryApplicationService = categoryApplicationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedModel<EntityModel<CategoryResponseDTO>>> getCategories(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "12") Integer size,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          @RequestParam(value = "username", required = false) String username
  ) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get all categories");
    Direction sortDireciton = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDireciton, "name"));
    return ResponseEntity.ok(categoryApplicationService.getCategories(pageable, username));
  }

  @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable(value = "id") UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to get category with id {}", id);
    return ResponseEntity.ok(categoryApplicationService.getCategoryById(id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") UUID id) {
    log.info("CONTROLLER: Request received from the client and passing to the application to delete category with id {}", id);
    categoryApplicationService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to create a new category");
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
    return ResponseEntity.created(location).body(categoryApplicationService.createCategory(categoryCreateDTO));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable(value = "id") UUID id, @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
    log.info("CONTROLLER: Request received from the client and passing to the application to update a category");
    return ResponseEntity.ok(categoryApplicationService.updateCategory(id, categoryUpdateDTO));
  }


}
