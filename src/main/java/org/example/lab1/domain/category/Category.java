package org.example.lab1.domain.category;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Category {
    Long id;
    String name;
}
