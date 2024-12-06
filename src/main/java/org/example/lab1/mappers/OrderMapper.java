package org.example.lab1.mappers;

import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.domain.order.Order;
import org.example.lab1.domain.product.Product;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(OrderEntity order);

    OrderEntity toOrderEntity(OrderDTO orderDTO);

    default List<ProductEntity> mapOrderEntriesToProducts(List<OrderEntryEntity> orderEntries) {
        return orderEntries.stream()
                .map(OrderEntryEntity::getProduct)
                .collect(Collectors.toList());
    }
}
