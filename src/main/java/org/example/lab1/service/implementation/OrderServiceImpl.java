package org.example.lab1.service.implementation;

import org.example.lab1.repository.OrderRepository;
import org.example.lab1.repository.ProductRepository;
import org.example.lab1.repository.entity.order.OrderEntity;
import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.order.OrderEntryId;
import org.example.lab1.repository.entity.product.ProductEntity;
import org.example.lab1.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.mappers.OrderMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        OrderEntity order = orderMapper.toOrderEntity(orderDTO);
        order.setOrderDate(LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public OrderDTO addProductToOrder(Long orderId, Long productId, int amount) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Перевіряємо, чи товар вже є у замовленні
        OrderEntryEntity existingEntry = order.getOrderEntries().stream()
                .filter(entry -> entry.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingEntry != null) {
            // Оновлюємо кількість, якщо товар вже є
            existingEntry.setAmount(existingEntry.getAmount() + amount);
        } else {
            // Додаємо новий товар у замовлення
            OrderEntryEntity newEntry = new OrderEntryEntity();
            newEntry.setOrder(order);
            newEntry.setProduct(product);
            newEntry.setAmount(amount);

            OrderEntryId entryId = new OrderEntryId();
            entryId.setOrderId(order.getId());
            entryId.setProductId(product.getId());
            newEntry.setId(entryId);

            order.getOrderEntries().add(newEntry);
        }

        OrderEntity updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO removeProductFromOrder(Long orderId, Long productId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Видаляємо товар із замовлення
        order.getOrderEntries().removeIf(entry -> entry.getProduct().getId().equals(productId));

        OrderEntity updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(updatedOrder);
    }
}
