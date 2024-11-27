package org.example.lab1.domain.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Category {
    long id;
    String name;
}
