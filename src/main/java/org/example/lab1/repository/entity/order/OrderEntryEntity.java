package org.example.lab1.repository.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lab1.repository.entity.product.ProductEntity;

@Entity
@Data
@Table(name = "order_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderEntryEntity {

    @EmbeddedId
    private OrderEntryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "amount", nullable = false)
    private int amount;
}
