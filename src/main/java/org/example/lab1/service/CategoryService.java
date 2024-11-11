package org.example.lab1.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO CategoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO updateCategory(@Parameter Long id, @Parameter CategoryDTO CategoryDTO);

    void deleteCategory(Long id);
}

