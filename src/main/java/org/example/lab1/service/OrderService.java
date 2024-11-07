package org.example.lab1.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO OrderDTO);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    OrderDTO updateOrder(@Parameter Long id, @Parameter OrderDTO OrderDTO);

    void deleteOrder(Long id);
}
