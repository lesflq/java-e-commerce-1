package org.example.lab1.domain;


import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Product {
    long id;
    String name;
    double price;
    long categoryId;
    String description;
}
