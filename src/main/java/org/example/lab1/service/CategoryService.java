package org.example.lab1.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO CategoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO updateCategory(@Parameter Long id, @Parameter CategoryDTO CategoryDTO);

    void deleteCategory(Long id);
}
