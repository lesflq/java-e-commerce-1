package org.example.lab1.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.repository.OrderEntryRepository;
import org.example.lab1.repository.OrderRepository;
import org.example.lab1.repository.ProductRepository;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.order.OrderEntryId;
import org.example.lab1.service.implementation.OrderEntryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderEntryServiceTest {

    @Mock
    private OrderEntryRepository orderEntryRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderEntryServiceImpl orderEntryService;

    private OrderEntity orderEntity;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setId(1L);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
    }

    @Test
    void testAddProductToOrder_Success() {
        // Given
        Long orderId = 1L;
        Long productId = 1L;
        int amount = 2;

        OrderEntryEntity orderEntry = new OrderEntryEntity(new OrderEntryId(orderId, productId), orderEntity, productEntity, amount);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(orderEntryRepository.save(any(OrderEntryEntity.class))).thenReturn(orderEntry);

        // When
        OrderEntryDTO result = orderEntryService.addProductToOrder(orderId, productId, amount);

        // Then
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertEquals(productId, result.getProductId());
        assertEquals(amount, result.getAmount());
    }

    @Test
    void testAddProductToOrder_OrderNotFound() {
        // Given
        Long orderId = 1L;
        Long productId = 1L;
        int amount = 2;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> orderEntryService.addProductToOrder(orderId, productId, amount));
    }

    @Test
    void testAddProductToOrder_ProductNotFound() {
        // Given
        Long orderId = 1L;
        Long productId = 1L;
        int amount = 2;

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> orderEntryService.addProductToOrder(orderId, productId, amount));
    }

    @Test
    void testRemoveProductFromOrder_Success() {
        // Given
        Long orderId = 1L;
        Long productId = 1L;

        OrderEntryId orderEntryId = new OrderEntryId(orderId, productId);
        doNothing().when(orderEntryRepository).deleteById(orderEntryId);

        // When
        orderEntryService.removeProductFromOrder(orderId, productId);

        // Then
        verify(orderEntryRepository, times(1)).deleteById(orderEntryId);
    }

    @Test
    void testGetOrderEntries_Success() {
        // Given
        Long orderId = 1L;

        OrderEntryEntity orderEntry1 = new OrderEntryEntity(new OrderEntryId(orderId, 1L), orderEntity, productEntity, 1);
        OrderEntryEntity orderEntry2 = new OrderEntryEntity(new OrderEntryId(orderId, 1L), orderEntity, productEntity, 3);

        when(orderEntryRepository.findByOrderId(orderId)).thenReturn(Arrays.asList(orderEntry1, orderEntry2));

        // When
        List<OrderEntryDTO> result = orderEntryService.getOrderEntries(orderId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetOrderEntries_OrderNotFound() {
        // Given
        Long orderId = 1L;

        when(orderEntryRepository.findByOrderId(orderId)).thenReturn(List.of());

        // When
        List<OrderEntryDTO> result = orderEntryService.getOrderEntries(orderId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

