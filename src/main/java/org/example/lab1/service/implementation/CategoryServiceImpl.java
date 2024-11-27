package org.example.lab1.service.implementation;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.domain.category.Category;
import org.example.lab1.mappers.CategoryMapper;
import org.example.lab1.repository.CategoryRepository;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.example.lab1.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO CategoryDTO) {
        CategoryEntity category = categoryMapper.toEntity(CategoryDTO);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = new ArrayList<>(categoryRepository.findAll());
        return categoryMapper.toCategoryDTOList(categories);
    }

    @Transactional
    public CategoryDTO getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"))
                .toBuilder().name(categoryDTO.getName()).build();
        CategoryEntity savedcategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedcategory);
    }


    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id); // видаляємо продукт з бази даних
    }

}