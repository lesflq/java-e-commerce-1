package org.example.lab1.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.domain.Order;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-31T19:03:55+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        return orderDTO;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }

    @Override
    public List<OrderDTO> toOrderDTOList(List<Order> ordersList) {
        return List.of();
    }

    @Override
    public List<Order> toOrderEntityList(List<OrderDTO> ordersDTOList) {
        return List.of();
    }


}
