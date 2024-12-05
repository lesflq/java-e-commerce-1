package org.example.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.exception.DatabaseException;
import org.example.lab1.mappers.ProductMapper;
import org.example.lab1.repository.CategoryRepository;
import org.example.lab1.repository.entity.category.CategoryEntity;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.service.ProductService;
import org.example.lab1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Transactional(propagation = Propagation.NESTED)
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

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = new ArrayList<>(productRepository.findAll());
        return productMapper.toProductDTOList(products);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toDTO(product);
    }

    @Transactional(propagation = Propagation.NESTED)
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Знаходимо продукт за ID
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Знаходимо категорію за ID
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Оновлюємо поля продукту
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        // Логування оновленого об'єкта
        System.out.println("Product updated: " + product);

        // Зберігаємо продукт
        ProductEntity savedProduct = productRepository.save(product);

        // Мапимо та повертаємо оновлений DTO
        return productMapper.toDTO(savedProduct);
    }


    @Transactional
    public void deleteProduct(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to delete category due to database error", ex);
        }
    }


}
