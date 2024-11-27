package org.example.lab1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.service.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIT {

    @MockBean
    private CategoryServiceImpl categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Test Category")
                .build();

        CategoryDTO createdCategory = CategoryDTO.builder()
                .id(1L)
                .name("Created Category")
                .build();

        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(createdCategory);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdCategory)));

        verify(categoryService, times(1)).createCategory(any(CategoryDTO.class));
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<CategoryDTO> categoryList = Arrays.asList(
                CategoryDTO.builder().id(1L).name("Category 1").build(),
                CategoryDTO.builder().id(2L).name("Category 2").build()
        );

        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryList)));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById() throws Exception {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Category 1")
                .build();

        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryDTO);

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryDTO)));

        verify(categoryService, times(1)).getCategoryById(anyLong());
    }

    @Test
    void testUpdateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Updated Category")
                .build();

        CategoryDTO updatedCategory = CategoryDTO.builder()
                .id(1L)
                .name("Updated Category")
                .build();

        when(categoryService.updateCategory(anyLong(), any(CategoryDTO.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCategory)));

        verify(categoryService, times(1)).updateCategory(anyLong(), any(CategoryDTO.class));
    }

    @Test
    void testDeleteCategory() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }
}

