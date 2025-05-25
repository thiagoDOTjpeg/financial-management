package br.com.gritti.app.infra.repository;

import br.com.gritti.app.domain.model.Category;
import br.com.gritti.app.domain.repository.CategoryRepository;
import br.com.gritti.app.infra.persistence.JpaCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
  private final JpaCategoryRepository jpaCategoryRepository;

  @Autowired
  public CategoryRepositoryImpl(JpaCategoryRepository jpaCategoryRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
  }

  @Override
  public Page<Category> findAll(Pageable pageable) {
    return jpaCategoryRepository.findAll(pageable);
  }

  @Override
  public Page<Category> findAllByUsername(Pageable pageable, String username) {
    return jpaCategoryRepository.findAllByUsername(username, pageable);
  }

  @Override
  public Optional<Category> findById(UUID id) {
    return jpaCategoryRepository.findById(id);
  }

  @Override
  public void save(Category category) {
    jpaCategoryRepository.save(category);
  }

  @Override
  public void delete(Category category) {
    jpaCategoryRepository.delete(category);
  }
}
