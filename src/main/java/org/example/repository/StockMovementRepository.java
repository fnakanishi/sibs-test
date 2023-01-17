package org.example.repository;

import org.example.entity.Item;
import org.example.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    Optional<StockMovement> findFirstByItemAndQuantityAndOrderIsNullOrderByCreationDate(Item item, int quantity);

    Optional<StockMovement> findById(Long id);

}
