package br.com.gritti.app.application.mapper;

import br.com.gritti.app.application.dto.category.CategoryCreateDTO;
import br.com.gritti.app.application.dto.category.CategoryResponseDTO;
import br.com.gritti.app.application.dto.category.CategoryUpdateDTO;
import br.com.gritti.app.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryResponseDTO categoryToCategoryResponseDTO(Category category);

  Category categoryCreateDTOtoCategory(CategoryCreateDTO categoryCreateDTO);

  Category categoryUpdateDTOtoCategory(CategoryUpdateDTO categoryUpdateDTO);

}
