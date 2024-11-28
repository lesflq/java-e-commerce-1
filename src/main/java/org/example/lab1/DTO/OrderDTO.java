package org.example.lab1.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;
import org.example.lab1.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderDTO {
    long id;
    List<OrderEntryDTO> orderEntries;
    LocalDateTime orderDate;
    String customerName;
    @Email
    String email;
}
