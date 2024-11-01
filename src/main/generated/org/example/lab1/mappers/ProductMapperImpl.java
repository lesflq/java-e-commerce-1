package org.example.lab1.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.domain.Product;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-31T19:03:54+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( product.getId() );
        productDTO.setName( product.getName() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setCategoryId( product.getCategoryId() );
        productDTO.setDescription( product.getDescription() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        double price = 0.0d;
        long categoryId = 0L;

        id = productDTO.getId();
        name = productDTO.getName();
        price = productDTO.getPrice();
        categoryId = productDTO.getCategoryId();

        Product product = new Product( id, name, price, categoryId );

        product.setDescription( productDTO.getDescription() );

        return product;
    }

    @Override
    public List<ProductDTO> toProductDTOList(List<Product> productList) {
        return List.of();
    }

    @Override
    public List<Product> toProductEntityList(List<ProductDTO> productDTOList) {
        return List.of();
    }


}
