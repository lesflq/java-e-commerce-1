package org.example.lab1.DTO;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderDTO {
    Long id;
    List<OrderEntryDTO> orderEntries;
    LocalDateTime orderDate;
    String customerName;
    @Email
    String email;
}
