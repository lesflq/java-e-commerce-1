package org.example.lab1.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;
import org.example.lab1.domain.order.OrderEntry;
import org.example.lab1.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderDTO {

    Long id;

    List<OrderEntry> orderProducts;
    @Past
    LocalDateTime orderDate;

    String customerName;


}
