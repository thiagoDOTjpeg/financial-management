package br.com.gritti.shared.mapper;

import br.com.gritti.domain.model.Category;
import br.com.gritti.shared.dto.response.summary.CategorySummaryResponse;

public class CategoryMapper {
  public static CategorySummaryResponse toCategorySummaryResponse(Category category) {
    CategorySummaryResponse response = new CategorySummaryResponse();
    response.setId(category.getId());
    response.setActive(category.getActive());
    response.setColor(category.getColor());
    response.setName(category.getName());
    response.setIcon(category.getIcon());
    response.setType(category.getType());
    return response;
  }
}
