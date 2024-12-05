package org.example.lab1.domain.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Product {
    Long id;
    String name;
    BigDecimal price;
    long categoryId;
    String description;
}
