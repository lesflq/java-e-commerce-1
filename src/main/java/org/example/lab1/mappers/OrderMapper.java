package org.example.lab1.mappers;

import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.domain.order.Order;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(OrderEntity order);

    OrderEntity toOrderEntity(OrderDTO orderDTO);

    List<OrderDTO> toOrderDTOList(List<Order> ordersList);

    List<OrderEntity> toOrderEntityList(List<OrderDTO> ordersDTOList);

    @Mapping(source = "orderProducts", target = "orderProducts")
    default List<OrderDTO> mapOrderEntries(List<OrderEntity> orderEntries) {
        return orderEntries.stream()
                .map(this::toOrderEntryDTO)
                .collect(Collectors.toList());
    }

    OrderDTO toOrderEntryDTO(OrderEntity orderEntry);
    Order toOrderEntryEntity(OrderDTO orderEntryDTO);
}

