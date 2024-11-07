package org.example.lab1.mappers;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
