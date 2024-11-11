package org.example.lab1.service.repositories;

import org.example.lab1.domain.order.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Long> {
}
