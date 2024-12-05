package org.example.lab1.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class CategoryDTO {
    Long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Category name must be between 3 and 50 characters")
    String name;


}
