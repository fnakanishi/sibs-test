package org.example.repository;

import org.example.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Boolean existsByName(String name);

    Optional<Item> findById(Long id);
}
