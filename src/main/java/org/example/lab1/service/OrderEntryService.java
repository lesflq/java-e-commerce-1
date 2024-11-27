package org.example.lab1.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.lab1.DTO.CategoryDTO;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.order.OrderEntryId;
import org.example.lab1.repository.entity.product.ProductEntity;

import java.util.List;

public interface OrderEntryService {

    OrderEntryDTO addProductToOrder(Long orderId, Long productId, int amount);

    void removeProductFromOrder(Long orderId, Long productId);

    List<OrderEntryDTO> getOrderEntries(Long orderId);
}
