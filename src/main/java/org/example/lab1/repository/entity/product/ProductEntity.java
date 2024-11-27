package org.example.lab1.repository.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lab1.repository.entity.category.CategoryEntity;

import java.math.BigDecimal;

@Entity
@Data
@Table(
        name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"})
)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "description")
    private String description;
}
