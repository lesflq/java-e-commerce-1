package org.example.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.mappers.ProductMapper;
import org.example.lab1.repository.CategoryRepository;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.service.ProductService;
import org.example.lab1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper,
                              ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));

        // Створюємо продукт
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category) // Установлюємо категорію
                .build();

        // Зберігаємо продукт
        ProductEntity savedProduct = productRepository.save(product);

        // Повертаємо DTO
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = new ArrayList<>(productRepository.findAll());
        return productMapper.toProductDTOList(products);
    }

    @Transactional
    public ProductDTO getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductEntity product = productRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Product not found"));

        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.toBuilder().name(productDTO.getName()).price(productDTO.getPrice())
                .description(productDTO.getDescription()).category(category).build();
        System.out.println("Product retrieved: " + product);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }


    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }


}
