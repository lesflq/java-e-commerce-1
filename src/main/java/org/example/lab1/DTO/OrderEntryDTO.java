package org.example.lab1.DTO;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Value;
import org.example.lab1.domain.order.Order;
import org.example.lab1.domain.product.Product;

@Value
@Builder(toBuilder = true)
public class OrderEntryDTO {

    Long id;

    Order order;

    Product product;

    int amount;
}
