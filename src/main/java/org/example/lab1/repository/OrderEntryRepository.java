package org.example.lab1.repository;

import org.example.lab1.repository.entity.order.OrderEntryEntity;
import org.example.lab1.repository.entity.order.OrderEntryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntryRepository extends JpaRepository<OrderEntryEntity, OrderEntryId> {
    List<OrderEntryEntity> findByOrderId(Long orderId);
}
