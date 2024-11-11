package org.example.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.domain.order.Order;
import org.example.lab1.domain.order.OrderEntry;
import org.example.lab1.domain.product.Product;
import org.example.lab1.mappers.OrderMapper;
import org.example.lab1.service.OrderService;
import org.example.lab1.service.repositories.OrderEntryRepository;
import org.example.lab1.service.repositories.OrderRepository;
import org.example.lab1.service.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEntryRepository orderEntryRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper; // Перетворення між Order та OrderDTO

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            OrderMapper orderMapper, OrderEntryRepository orderEntryRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.orderEntryRepository = orderEntryRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrderEntity(orderDTO).toBuilder()
                .orderDate(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }


    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDTO addProductToOrder(Long orderId, Long productId, int amount) {
        Order newOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        OrderEntry orderEntry = OrderEntry.builder().order(newOrder).product(product)
                        .amount(amount).build();

        newOrder.getOrderProducts().add(orderEntry);  // додаємо orderEntry до списку замовлення

        // Зберігаємо тільки order, щоб викликати каскадне збереження
        orderRepository.save(newOrder);

        return orderMapper.toOrderDTO(newOrder);
    }

    @Transactional
    public OrderDTO removeProductFromOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.getOrderProducts().removeIf(entry -> entry.getProduct().getId() == productId);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }
}

