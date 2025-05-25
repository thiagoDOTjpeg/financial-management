package br.com.gritti.app.domain.repository;

import br.com.gritti.app.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
  Page<Category> findAll(Pageable pageable);
  Page<Category> findAllByUsername(Pageable pageable, String username);
  Optional<Category> findById(UUID id);
  void save(Category category);
  void delete(Category category);
}
