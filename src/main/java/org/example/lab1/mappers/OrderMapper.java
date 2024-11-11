package org.example.lab1.mappers;

import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.domain.order.Order;
import org.example.lab1.domain.order.OrderEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    Order toOrderEntity(OrderDTO orderDTO);

    List<OrderDTO> toOrderDTOList(List<Order> ordersList);

    List<Order> toOrderEntityList(List<OrderDTO> ordersDTOList);

    @Mapping(source = "orderProducts", target = "orderProducts")
    default List<OrderEntryDTO> mapOrderEntries(List<OrderEntry> orderEntries) {
        return orderEntries.stream()
                .map(this::toOrderEntryDTO)
                .collect(Collectors.toList());
    }

    OrderEntryDTO toOrderEntryDTO(OrderEntry orderEntry);
    OrderEntry toOrderEntryEntity(OrderEntryDTO orderEntryDTO);
}


