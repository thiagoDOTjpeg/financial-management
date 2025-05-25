package br.com.gritti.app.domain.service;

import br.com.gritti.app.domain.model.Category;
import br.com.gritti.app.infra.repository.CategoryRepositoryImpl;
import br.com.gritti.app.shared.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryDomainService {
  private final Logger log = LoggerFactory.getLogger(CategoryDomainService.class);
  private final CategoryRepositoryImpl categoryRepositoryImpl;

  @Autowired
  public CategoryDomainService(CategoryRepositoryImpl categoryRepositoryImpl) {
    this.categoryRepositoryImpl = categoryRepositoryImpl;
  }

  public Page<Category> getCategories(Pageable pageable) {
    log.info("DOMAIN: Request received from application and getting all categories from the repository");
    return categoryRepositoryImpl.findAll(pageable);
  }

  public Page<Category> getCategories(Pageable pageable, String username) {
    log.info("DOMAIN: Request received from application and getting all categories from the repository and filtering by username: {}", username);
    return categoryRepositoryImpl.findAllByUsername(pageable, username);
  }

  public Category getCategoryById(UUID id) {
    log.info("DOMAIN: Request received from application and getting category with id {} from the repository", id);
    return categoryRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
  }

  public void createCategory(Category category) {
    log.info("DOMAIN: Request received from application and saving a new category from the repository");
    categoryRepositoryImpl.save(category);
  }

  public void deleteCategory(UUID id) {
    log.info("DOMAIN: Request received from application and deleting category with id {}", id);
    Category category = categoryRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    categoryRepositoryImpl.delete(category);
  }

  public Category updateCategory(UUID id, Category category) {
    log.info("DOMAIN: Request received from application and updating the category with id {}", category.getId());
    Category entity = categoryRepositoryImpl.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    entity.setName(category.getName());
    entity.setType(category.getType());
    categoryRepositoryImpl.save(entity);
    return entity;
  }

}
