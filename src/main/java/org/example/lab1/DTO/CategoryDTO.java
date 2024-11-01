package org.example.lab1.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    private long id;
    @NotNull
    @Size(min = 2, max = 50, message = "Category name must be between 3 and 50 characters")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 2, max = 50, message = "Category name must be between 3 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 50, message = "Category name must be between 3 and 50 characters") String name) {
        this.name = name;
    }
}
