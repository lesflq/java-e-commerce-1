package org.example.lab1.controllers;

import jakarta.validation.Valid;
import org.example.lab1.DTO.OrderDTO;
import org.example.lab1.DTO.ProductDTO;
import org.example.lab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{orderId}/addProduct")
    public ResponseEntity<OrderDTO> addProductToOrder(
            @PathVariable Long orderId,
            @RequestParam Long productId,
            @RequestParam int amount) {
        OrderDTO updatedOrder = orderService.addProductToOrder(orderId, productId, amount);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}/removeProduct")
    public ResponseEntity<OrderDTO> removeProductFromOrder(
            @PathVariable Long orderId,
            @RequestParam Long productId) {
        OrderDTO updatedOrder = orderService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok(updatedOrder);
    }
}
