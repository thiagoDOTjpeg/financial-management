package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.category.CategoryCreateDTO;
import br.com.gritti.app.application.dto.category.CategoryResponseDTO;
import br.com.gritti.app.application.dto.category.CategoryUpdateDTO;
import br.com.gritti.app.application.mapper.CategoryMapper;
import br.com.gritti.app.domain.model.Category;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.CategoryDomainService;
import br.com.gritti.app.interfaces.controller.CategoryController;
import br.com.gritti.app.shared.util.SecurityUtil;
import br.com.gritti.app.shared.util.hateoas.CategoryHateoasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

@Service
public class CategoryApplicationService {
  private final Logger log = LoggerFactory.getLogger(CategoryApplicationService.class);

  private final CategoryDomainService categoryDomainService;
  private final PagedResourcesAssembler<CategoryResponseDTO> assembler;
  private final CategoryMapper categoryMapper;

  @Autowired
  public CategoryApplicationService(CategoryDomainService categoryDomainService,
                                    PagedResourcesAssembler<CategoryResponseDTO> assembler, CategoryMapper categoryMapper) {
    this.categoryDomainService = categoryDomainService;
    this.assembler = assembler;
    this.categoryMapper = categoryMapper;
  }

  public PagedModel<EntityModel<CategoryResponseDTO>> getCategories(Pageable pageable, String username) {
    log.info("APPLICATION: Request received from controller and passing to domain to get all categories");
    String currentUsername = SecurityUtil.getCurrentUsername();
    boolean isAdmin = SecurityUtil.isAdmin();
    String usernameToUse = isAdmin ? username : currentUsername;
    Page<Category> categories;
    if(username != null && !username.isBlank()) {
      if(!isAdmin && !username.equals(currentUsername)){
        throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
      }
      categories = categoryDomainService.getCategories(pageable, usernameToUse);
    } else {
      categories = categoryDomainService.getCategories(pageable);
    }

    Page<CategoryResponseDTO> categoriesWithLinks = categories.map(c -> {
      CategoryResponseDTO categoryResponseDTO = categoryMapper.categoryToCategoryResponseDTO(c);
      CategoryHateoasUtil.addLinks(categoryResponseDTO);
      return categoryResponseDTO;
    });

    Link findAllLinks = linkTo(methodOn(CategoryController.class).getCategories(pageable.getPageNumber(), pageable.getPageSize(), "asc", "")).withSelfRel();
    Link createLinks = linkTo(methodOn(CategoryController.class).createCategory(new CategoryCreateDTO())).withRel("create");
    PagedModel<EntityModel<CategoryResponseDTO>> pagedModel = assembler.toModel(categoriesWithLinks, findAllLinks);
    pagedModel.add(createLinks);
    return pagedModel;
  }

  public CategoryResponseDTO getCategoryById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to get a category by id: {}", id);
    Category category = categoryDomainService.getCategoryById(id);
    return categoryMapper.categoryToCategoryResponseDTO(category);
  }

  public CategoryResponseDTO createCategory(CategoryCreateDTO categoryCreateDTO) {
    log.info("APPLICATION: Request received from controller and passing to the domain to create a new category");
    Category category = categoryMapper.categoryCreateDTOtoCategory(categoryCreateDTO);
    User currentUser = SecurityUtil.getCurrentUser();
    if(currentUser == null)  throw new  AccessDeniedException("Access denied, you must be logged in to use this resource");
    category.setUser(currentUser);
    categoryDomainService.createCategory(category);
    return categoryMapper.categoryToCategoryResponseDTO(category);
  }

  public void deleteCategory(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to delete a category by id: {}", id);
    categoryDomainService.deleteCategory(id);
  }

  public CategoryResponseDTO updateCategory(UUID id, CategoryUpdateDTO categoryUpdateDTO) {
    log.info("APPLICATION: Request received from controller and passing to the domain to update a category by id: {}", id);
    Category category = categoryMapper.categoryUpdateDTOtoCategory(categoryUpdateDTO);
    Category updatedCategory = categoryDomainService.updateCategory(id, category);
    return categoryMapper.categoryToCategoryResponseDTO(updatedCategory);
  }
}
