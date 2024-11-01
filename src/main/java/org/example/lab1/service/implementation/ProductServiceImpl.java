package org.example.lab1.service.implementation;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.domain.Product;
import org.example.lab1.mappers.ProductMapper;
import org.example.lab1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final Map<Long, Product> mockProductDatabase = new HashMap<>(); // Plug for saving data
    private Long productIdSequence = 1L; // Simulation of auto increments of an ID

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setId(productIdSequence++); // set unique ID
        mockProductDatabase.put(product.getId(), product); // save data in "plug-database"
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = new ArrayList<>(mockProductDatabase.values());
        return productMapper.toProductDTOList(products);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = mockProductDatabase.get(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = mockProductDatabase.get(id);
        System.out.println("Product retrieved: " + product);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategoryId(productDTO.getCategoryId());
        mockProductDatabase.put(id, product); // insert updated data
        return productMapper.toDTO(product);
    }


    @Override
    public void deleteProduct(Long id) {
        if (!mockProductDatabase.containsKey(id)) {
            throw new RuntimeException("Product not found");
        }
        mockProductDatabase.remove(id); // видаляємо продукт
    }


}
