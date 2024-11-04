package org.example.lab1.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;
import org.example.lab1.domain.Product;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderDTO {
    long id;
    List<Product> products;
    @NotNull
    @Past
    LocalDateTime orderDate;
    String customerName;


}
