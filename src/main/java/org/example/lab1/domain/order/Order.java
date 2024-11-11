package org.example.lab1.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.example.lab1.domain.product.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntry> orderProducts;

    private LocalDateTime orderDate;

    private String customerName;

    public void addOrderProduct(OrderEntry orderEntry) {
        orderProducts.add(orderEntry);
        orderEntry.setOrder(this); // встановлює зворотну асоціацію
    }

}
