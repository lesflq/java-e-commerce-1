package org.example.lab1.mappers;

import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.domain.Order;
import org.example.lab1.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(target = "products", source = "products")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "customerName", source = "customerName")
    @Named("toDTO")
    OrderDTO toDTO(Order order);

    @Mapping(target = "products", source = "products")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "customerName", source = "customerName")
    @Named("toEntity")
    Order toEntity(OrderDTO orderDTO);

    List<OrderDTO> toOrderDTOList(List<Order> ordersList);
    List<Order> toOrderEntityList(List<OrderDTO> ordersDTOList);


}

