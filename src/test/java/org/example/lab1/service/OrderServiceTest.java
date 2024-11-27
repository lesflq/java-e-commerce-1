package org.example.lab1.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.mappers.OrderMapper;
import org.example.lab1.repository.OrderRepository;
import org.example.lab1.repository.ProductRepository;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.service.implementation.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testCreateOrder_Success() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build();

        when(orderMapper.toOrderEntity(orderDTO)).thenReturn(orderEntity);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderMapper.toOrderDTO(any(OrderEntity.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
    }

    @Test
    void testGetOrderById_Success() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderMapper.toOrderDTO(any(OrderEntity.class))).thenReturn(OrderDTO.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build());

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void testDeleteOrder_Success() {
        // Мокуємо виклик existsById, щоб повертав true
        when(orderRepository.existsById(1L)).thenReturn(true);

        // Викликаємо метод, який перевіряє існування замовлення і видаляє його
        orderService.deleteOrder(1L);

        // Перевіряємо, чи було викликано метод deleteById з правильним аргументом
        verify(orderRepository).deleteById(1L);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void testAddProductToOrder_Success() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name("Product")
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .orderEntries(new ArrayList<>())  // Ініціалізація списку
                .build();

        OrderEntryEntity orderEntryEntity = new OrderEntryEntity();
        orderEntryEntity.setProduct(productEntity);
        orderEntryEntity.setAmount(2);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderMapper.toOrderDTO(any(OrderEntity.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.addProductToOrder(1L, 1L, 2);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testAddProductToOrder_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.addProductToOrder(1L, 1L, 2));
    }

    @Test
    void testAddProductToOrder_ProductNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new OrderEntity()));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.addProductToOrder(1L, 1L, 2));
    }

    @Test
    void testRemoveProductFromOrder_Success() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name("Product")
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .orderEntries(new ArrayList<>())  // Ініціалізація списку
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderMapper.toOrderDTO(any(OrderEntity.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.removeProductFromOrder(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testRemoveProductFromOrder_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.removeProductFromOrder(1L, 1L));
    }
}

