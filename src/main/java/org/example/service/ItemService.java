package org.example.service;

import org.example.entity.Item;
import org.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository repository;

    public Boolean existByName(String name) {
        return repository.existsByName(name);
    }

    private Item save(Item item) {
        return repository.save(item);
    }

    public Item save(String name) {
        Item item = new Item();
        item.setName(name);
        return save(item);
    }

    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    public Item update(Item item, String name) {
        item.setName(name);

        return save(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public List<Item> findAll() {
        return repository.findAll();
    }
}
