package org.example.lab1.repository.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntryId implements Serializable {

    @Column(name = "order_id", columnDefinition = "UUID")

    private Long orderId;

    @Column(name = "product_id", columnDefinition = "UUID")
    private Long productId;

}
