package org.example.repository;

import org.example.entity.Item;
import org.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findFirstByItemAndQuantityAndStockMovementIsNullOrderByCreationDate(Item item, int quantity);

    Optional<Order> findById(Long id);
}
