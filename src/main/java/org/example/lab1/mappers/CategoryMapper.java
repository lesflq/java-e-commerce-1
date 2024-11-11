package org.example.lab1.mappers;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.domain.category.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toCategoryDTOList(List<Category> categoryList);

    List<CategoryDTO> toCategoryEntityList(List<CategoryDTO> categoryDTOList);
}
