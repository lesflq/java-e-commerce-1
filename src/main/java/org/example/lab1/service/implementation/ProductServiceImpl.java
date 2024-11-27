package org.example.lab1.service.implementation;

import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.domain.Product;
import org.example.lab1.mappers.ProductMapper;
import org.example.lab1.service.ProductService;
import org.example.lab1.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public List<ProductDTO> getAllProducts() {
        List<Product> products = new ArrayList<>(productRepository.findAll());
        return productMapper.toProductDTOList(products);
    }

    @Transactional
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Product not found"))
                .toBuilder().name(productDTO.getName()).price(productDTO.getPrice())
                .description(productDTO.getDescription()).categoryId(productDTO.getCategoryId()).build();
        System.out.println("Product retrieved: " + product);
        Product savedProduct = productRepository.save(product);
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
