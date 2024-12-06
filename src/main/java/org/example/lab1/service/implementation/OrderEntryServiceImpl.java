package org.example.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lab1.DTO.OrderEntryDTO;
import org.example.lab1.repository.OrderEntryRepository;
import org.example.lab1.repository.OrderRepository;
import org.example.lab1.repository.ProductRepository;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.order.OrderEntryId;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.service.OrderEntryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEntryServiceImpl implements OrderEntryService {

    private final OrderEntryRepository orderEntryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.NESTED)
    public OrderEntryDTO addProductToOrder(Long orderId, Long productId, int amount) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        OrderEntryId id = new OrderEntryId(orderId, productId);
        OrderEntryEntity orderEntry = new OrderEntryEntity(id, order, product, amount);

        OrderEntryEntity savedOrderEntry = orderEntryRepository.save(orderEntry);

        return new OrderEntryDTO(savedOrderEntry.getOrder().getId(), savedOrderEntry.getProduct().getId(), savedOrderEntry.getAmount());
    }

    @Transactional
    public void removeProductFromOrder(Long orderId, Long productId) {
        OrderEntryId id = new OrderEntryId(orderId, productId);
        orderEntryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderEntryDTO> getOrderEntries(Long orderId) {
        List<OrderEntryEntity> entries = orderEntryRepository.findByOrderId(orderId);
        return entries.stream()
                .map(entry -> new OrderEntryDTO(entry.getOrder().getId(), entry.getProduct().getId(), entry.getAmount()))
                .toList();
    }
}

