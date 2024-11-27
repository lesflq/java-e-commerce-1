package org.example.lab1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.service.ProductService;
import org.example.lab1.service.implementation.OrderServiceImpl;
import org.example.lab1.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIT {

    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .products(new ArrayList<>())
                .orderDate(LocalDateTime.now())
                .customerName("John Doe")
                .email("john.doe@example.com")
                .build();

        OrderDTO createdOrder = OrderDTO.builder()
                .id(1L)
                .products(new ArrayList<>())
                .orderDate(LocalDateTime.now())
                .customerName("John Doe")
                .email("john.doe@example.com")
                .build();

        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(createdOrder);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdOrder)));

        verify(orderService, times(1)).createOrder(any(OrderDTO.class));
    }

    @Test
    void testGetOrderById() throws Exception {
        Long orderId = 1L;
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .products(new ArrayList<>())
                .orderDate(LocalDateTime.now())
                .customerName("John Doe")
                .email("john.doe@example.com")
                .build();

        when(orderService.getOrderById(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderDTO)));

        verify(orderService, times(1)).getOrderById(anyLong());
    }

    @Test
    void testDeleteOrderById() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(anyLong());
    }

    @Test
    void testAddProductToOrder() throws Exception {
        Long orderId = 1L;
        Long productId = 101L;
        int amount = 2;

        OrderDTO updatedOrder = OrderDTO.builder()
                .id(orderId)
                .products(new ArrayList<>())
                .orderDate(LocalDateTime.now())
                .customerName("John Doe")
                .email("john.doe@example.com")
                .build();

        when(orderService.addProductToOrder(orderId, productId, amount)).thenReturn(updatedOrder);

        mockMvc.perform(post("/api/v1/orders/{orderId}/addProduct", orderId)
                        .param("productId", productId.toString())
                        .param("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedOrder)));

        verify(orderService, times(1)).addProductToOrder(anyLong(), anyLong(), anyInt());
    }

    @Test
    void testRemoveProductFromOrder() throws Exception {
        Long orderId = 1L;
        Long productId = 101L;

        OrderDTO updatedOrder = OrderDTO.builder()
                .id(orderId)
                .products(new ArrayList<>()) // Remove product from the order (assumed empty)
                .orderDate(LocalDateTime.now())
                .customerName("John Doe")
                .email("john.doe@example.com")
                .build();

        when(orderService.removeProductFromOrder(orderId, productId)).thenReturn(updatedOrder);

        mockMvc.perform(delete("/api/v1/orders/{orderId}/removeProduct", orderId)
                        .param("productId", productId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedOrder)));

        verify(orderService, times(1)).removeProductFromOrder(anyLong(), anyLong());
    }
}
