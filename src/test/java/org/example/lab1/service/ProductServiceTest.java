package org.example.lab1.service;

import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.domain.Product;
import org.example.lab1.mappers.ProductMapper;
import org.example.lab1.service.ProductService;
import org.example.lab1.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);
        productDTO.setDescription("Test Description");
        productDTO.setCategoryId(1L);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setCategoryId(1L);

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);
    }

    @Test
    void testCreateProduct() {
        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        verify(productMapper).toEntity(productDTO);
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetAllProducts() {
        when(productMapper.toProductDTOList(anyList())).thenReturn(List.of(productDTO));

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productMapper).toProductDTOList(anyList());
    }

    @Test
    void testGetProductById() {
        productService.createProduct(productDTO); // Збереження продукту для подальшого пошуку

        ProductDTO foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());

        // Оскільки `toDTO` викликається двічі: один раз у `createProduct` і один раз у `getProductById`
        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        productService.createProduct(productDTO); // Збереження продукту для подальшого оновлення

        productDTO.setName("Updated Product");
        productDTO.setPrice(150.0);

        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(150.0, updatedProduct.getPrice());

        // Оскільки `toDTO` викликається двічі: один раз у `createProduct` і один раз у `updateProduct`
        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        productService.createProduct(productDTO);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found", exception.getMessage());
    }
}
