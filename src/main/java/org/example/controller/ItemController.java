package org.example.controller;

import org.example.controller.request.ItemRequest;
import org.example.controller.response.MessageResponse;
import org.example.entity.Item;
import org.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ItemRequest request) {
        String name = request.getName();

        if (itemService.existByName(name)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Item name is already in the database!"));
        }

        Item item = itemService.save(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        Optional<Item> itemOpt = itemService.findById(id);
        if (!itemOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Item could not be found!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(itemOpt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ItemRequest request) {
        String name = request.getName();

        Optional<Item> itemOpt = itemService.findById(id);
        if (!itemOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Item could not be found!"));
        }

        Item item = itemService.update(itemOpt.get(), name);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Item> itemOpt = itemService.findById(id);
        if (!itemOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Item could not be found!"));
        }
        
        itemService.delete(itemOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list-all")
    public ResponseEntity listAll() {
        List<Item> itemList = itemService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(itemList);
    }
}