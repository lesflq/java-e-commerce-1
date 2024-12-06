package org.example.lab1.mappers;

import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId") // Мапимо ID категорії на categoryId
    ProductDTO toDTO(ProductEntity product);

    @Mapping(source = "categoryId", target = "category.id") // Мапимо categoryId на об'єкт CategoryEntity
    ProductEntity toEntity(ProductDTO productDTO);

    List<ProductDTO> toProductDTOList(List<ProductEntity> productList);
    List<ProductEntity> toProductEntityList(List<ProductDTO> productDTOList);

}
