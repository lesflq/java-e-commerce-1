package org.example.lab1.service.implementation;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.exception.DatabaseException;
import org.example.lab1.mappers.CategoryMapper;
import org.example.lab1.repository.CategoryRepository;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.example.lab1.service.CategoryService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(propagation = Propagation.NESTED)
    public CategoryDTO createCategory(CategoryDTO CategoryDTO) {
        CategoryEntity category = categoryMapper.toEntity(CategoryDTO);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = new ArrayList<>(categoryRepository.findAll());
        return categoryMapper.toCategoryDTOList(categories);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.toDTO(category);
    }

    @Transactional(propagation = Propagation.NESTED)
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"))
                .toBuilder().name(categoryDTO.getName()).build();
        CategoryEntity savedcategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedcategory);
    }


    @Transactional
    public void deleteCategory(Long id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
            }
        } catch (RuntimeException ex) {
            throw new DatabaseException("Failed to delete category due to database error", ex);
        }
    }

}