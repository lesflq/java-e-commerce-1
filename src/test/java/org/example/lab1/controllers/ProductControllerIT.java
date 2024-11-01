package org.example.lab1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.service.ProductService;
import org.example.lab1.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product Star"); // Обов'язкове поле
        productDTO.setPrice(100.0);         // Обов'язкове поле

        ProductDTO createdProduct = new ProductDTO();
        createdProduct.setName("Test Product Comet");
        createdProduct.setPrice(1523.0);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdProduct)));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDTO> productList = new ArrayList<>(); // Заповни даними продуктів

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productList)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        Long productId = 1L;
        ProductDTO product = new ProductDTO(); // Заповни даними продукту

        when(productService.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/v1/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));

        verify(productService, times(1)).getProductById(anyLong());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product Star"); // Обов'язкове поле
        productDTO.setPrice(100.0);         // Обов'язкове поле

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setName("Test Product Comet");
        updatedProduct.setPrice(1902.0); // Заповни даними оновленого продукту

        when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedProduct)));

        verify(productService, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/v1/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(anyLong());
    }
}
