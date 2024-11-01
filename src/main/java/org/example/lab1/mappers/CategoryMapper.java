package org.example.lab1.mappers;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "name", source = "name")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "name", source = "name")
    Category toEntity(CategoryDTO categoryDTO);
}
