package org.example.lab1.mappers;

import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.domain.Order;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    Order toOrderEntity(OrderDTO orderDTO);

    List<OrderDTO> toOrderDTOList(List<Order> ordersList);
    List<Order> toOrderEntityList(List<OrderDTO> ordersDTOList);


}

