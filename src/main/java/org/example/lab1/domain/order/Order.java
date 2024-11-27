package org.example.lab1.domain.order;

import lombok.Builder;
import lombok.Value;
import org.example.lab1.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;
@Value
@Builder(toBuilder = true)
public class Order {
    long id;
    List<Product> products;
    LocalDateTime orderDate;
    String customerName;
    String email;

}