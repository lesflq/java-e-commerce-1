package org.example.lab1.service;

import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.exception.DatabaseException;
import org.example.lab1.mappers.CategoryMapper;
import org.example.lab1.repository.CategoryRepository;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.example.lab1.service.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Test
    void testCreateCategory_Success() {
        // Підготовка
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("Category1")
                .build();

        when(categoryMapper.toEntity(categoryDTO)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toDTO(categoryEntity)).thenReturn(categoryDTO);

        // Виконання
        CategoryDTO result = categoryService.createCategory(categoryDTO);

        // Перевірки
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Category1", result.getName());
        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void testGetAllCategories_Success() {
        // Підготовка
        CategoryEntity category1 = CategoryEntity.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryEntity category2 = CategoryEntity.builder()
                .id(2L)
                .name("Category2")
                .build();

        List<CategoryEntity> categoryEntities = Arrays.asList(category1, category2);

        CategoryDTO categoryDTO1 = CategoryDTO.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryDTO categoryDTO2 = CategoryDTO.builder()
                .id(2L)
                .name("Category2")
                .build();

        when(categoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryMapper.toCategoryDTOList(categoryEntities)).thenReturn(Arrays.asList(categoryDTO1, categoryDTO2));

        // Виконання
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Перевірки
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Category1", result.get(0).getName());
        assertEquals("Category2", result.get(1).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void testGetCategoryById_Success() {
        // Підготовка
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Category1")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toDTO(categoryEntity)).thenReturn(categoryDTO);

        // Виконання
        CategoryDTO result = categoryService.getCategoryById(1L);

        // Перевірки
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Category1", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void testUpdateCategory_Success() {
        // Підготовка
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("UpdatedCategory")
                .build();

        CategoryEntity existingCategory = CategoryEntity.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryEntity updatedCategory = existingCategory.toBuilder()
                .name("UpdatedCategory")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDTO(updatedCategory)).thenReturn(categoryDTO);

        // Виконання
        CategoryDTO result = categoryService.updateCategory(1L, categoryDTO);

        // Перевірки
        assertNotNull(result);
        assertEquals("UpdatedCategory", result.getName());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(updatedCategory);
    }

    @Test
    void testDeleteCategory_Success() {
        // Підготовка
        when(categoryRepository.existsById(1L)).thenReturn(true);

        // Виконання
        categoryService.deleteCategory(1L);

        // Перевірки
        verify(categoryRepository).deleteById(1L);
    }
}
