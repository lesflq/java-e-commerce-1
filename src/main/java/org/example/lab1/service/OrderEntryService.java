package org.example.lab1.service;

import org.example.lab1.DTO.OrderEntryDTO;

import java.util.List;
import java.util.UUID;

public interface OrderEntryService {

    OrderEntryDTO addProductToOrder(Long orderId, Long productId, int amount);

    void removeProductFromOrder(Long orderId, Long productId);

    List<OrderEntryDTO> getOrderEntries(Long orderId);
}
