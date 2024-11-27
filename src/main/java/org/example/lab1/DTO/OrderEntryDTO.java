package org.example.lab1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntryDTO {
    private Long orderId;
    private Long productId;
    private int amount;
}
