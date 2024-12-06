package org.example.lab1.service.implementation;

import org.example.lab1.exception.DatabaseException;
import org.example.lab1.repository.OrderRepository;
import org.example.lab1.repository.ProductRepository;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.mappers.OrderMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(propagation = Propagation.NESTED)
    public OrderDTO createOrder(OrderDTO orderDTO) {

        OrderEntity order = orderMapper.toOrderEntity(orderDTO);
        order.setOrderDate(LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        try {
            if (orderRepository.existsById(orderId)) {
                orderRepository.deleteById(orderId);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to delete category due to database error", ex);
        }
    }


}
