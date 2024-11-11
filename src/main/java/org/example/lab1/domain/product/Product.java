package org.example.lab1.domain.product;


import jakarta.persistence.*;
import lombok.*;
import org.example.lab1.domain.order.OrderEntry;

import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String name;
    double price;
    long categoryId;
    String description;

    @OneToMany(mappedBy = "product")
    private List<OrderEntry> orderProducts;

}
