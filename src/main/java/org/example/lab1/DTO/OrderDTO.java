package org.example.lab1.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.example.lab1.domain.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private long id;
    private List<Product> products;
    @NotNull
    @Past
    private LocalDateTime orderDate;
    private String customerName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public @NotNull @Past LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(@NotNull @Past LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
