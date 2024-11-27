package org.example.lab1.service;

import org.example.lab1.DTO.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    void deleteOrder(Long id);

    OrderDTO addProductToOrder(Long orderId, Long productId, int amount);

    OrderDTO removeProductFromOrder(Long orderId, Long productId);
}