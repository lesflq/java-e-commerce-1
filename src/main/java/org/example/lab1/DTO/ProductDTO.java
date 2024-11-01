package org.example.lab1.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.lab1.validation.CosmicWordCheck;

public class ProductDTO {
    private long id;
    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    @CosmicWordCheck(message = "Product name must contain a cosmic term like 'star', 'galaxy', or 'comet'")
    private String name;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private double price;
    @NotNull(message = "Category ID cannot be null")
    private long categoryId;
    private String description;

    public ProductDTO(long id, String name, double price, long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
