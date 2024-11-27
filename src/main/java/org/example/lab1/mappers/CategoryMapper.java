package org.example.lab1.mappers;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.domain.category.Category;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(CategoryEntity category);

    CategoryEntity toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toCategoryDTOList(List<CategoryEntity> categoryList);

    List<CategoryEntity> toCategoryEntityList(List<CategoryDTO> categoryDTOList);
}