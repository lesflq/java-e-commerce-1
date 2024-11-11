package org.example.lab1.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.lab1.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    void deleteOrder(Long id);

    OrderDTO addProductToOrder(Long orderId, Long productId, int amount);

    OrderDTO removeProductFromOrder(Long orderId, Long productId);
}
