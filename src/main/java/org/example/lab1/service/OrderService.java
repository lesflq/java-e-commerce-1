package org.example.lab1.service;

import org.example.lab1.DTO.OrderDTO;

import java.util.UUID;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    void deleteOrder(Long id);

}