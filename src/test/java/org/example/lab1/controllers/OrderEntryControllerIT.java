package org.example.lab1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.service.OrderEntryService;
import org.example.lab1.service.implementation.OrderEntryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderEntryControllerIT {

    @MockBean
    private OrderEntryServiceImpl orderEntryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddProductToOrder() throws Exception {
        Long orderId = 1L;
        Long productId = 101L;
        int amount = 2;

        OrderEntryDTO orderEntryDTO = OrderEntryDTO.builder()
                .orderId(orderId)
                .productId(productId)
                .amount(amount)
                .build();

        when(orderEntryService.addProductToOrder(orderId, productId, amount)).thenReturn(orderEntryDTO);

        mockMvc.perform(post("/api/v1/order/entry/{orderId}/products", orderId)
                        .param("productId", productId.toString())
                        .param("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(orderEntryDTO)));

        verify(orderEntryService, times(1)).addProductToOrder(anyLong(), anyLong(), anyInt());
    }

    @Test
    void testRemoveProductFromOrder() throws Exception {
        Long orderId = 1L;
        Long productId = 101L;

        mockMvc.perform(delete("/api/v1/order/entry/{orderId}/products/{productId}", orderId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderEntryService, times(1)).removeProductFromOrder(anyLong(), anyLong());
    }

    @Test
    void testGetOrderEntries() throws Exception {
        Long orderId = 1L;
        List<OrderEntryDTO> orderEntries = Arrays.asList(
                OrderEntryDTO.builder().orderId(orderId).productId(101L).amount(2).build(),
                OrderEntryDTO.builder().orderId(orderId).productId(102L).amount(1).build()
        );

        when(orderEntryService.getOrderEntries(orderId)).thenReturn(orderEntries);

        mockMvc.perform(get("/api/v1/order/entry/{orderId}/products", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderEntries)));

        verify(orderEntryService, times(1)).getOrderEntries(anyLong());
    }
}
