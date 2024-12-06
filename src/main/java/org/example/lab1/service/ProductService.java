package org.example.lab1.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(@Parameter Long id, @Parameter ProductDTO productDTO);
    void deleteProduct(Long id);
}