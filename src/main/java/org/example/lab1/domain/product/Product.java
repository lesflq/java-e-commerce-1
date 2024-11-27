package org.example.lab1.domain.product;

import lombok.*;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class Product {
    long id;
    String name;
    BigDecimal price;
    long categoryId;
    String description;
}
