package org.example.controller;

import org.example.controller.request.StockMovementRequest;
import org.example.controller.response.MessageResponse;
import org.example.entity.StockMovement;
import org.example.exception.stockmovement.StockMovementException;
import org.example.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stockMovement")
public class StockMovementController {
    @Autowired private StockMovementService stockMovementService;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody StockMovementRequest request) {
        Long itemId = request.getItemId();
        int quantity = request.getQuantity();

        try {
            StockMovement stockMovement = stockMovementService.create(itemId, quantity);
            stockMovementService.resolveStockMovement(stockMovement);
            return ResponseEntity.status(HttpStatus.CREATED).body(stockMovement);
        } catch (StockMovementException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        Optional<StockMovement> stockMovementOpt = stockMovementService.findById(id);
        if (!stockMovementOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: StockMovement could not be found!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(stockMovementOpt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody int quantity) {
        Optional<StockMovement> stockMovementOpt = stockMovementService.findById(id);
        if (!stockMovementOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: StockMovement could not be found!"));
        }

        try {
            StockMovement stockMovement = stockMovementService.update(stockMovementOpt.get(), quantity);
            return ResponseEntity.status(HttpStatus.OK).body(stockMovement);
        } catch (StockMovementException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<StockMovement> stockMovementOpt = stockMovementService.findById(id);
        if (!stockMovementOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: StockMovement could not be found!"));
        }

        stockMovementService.delete(stockMovementOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list-all")
    public ResponseEntity listAll() {
        List<StockMovement> stockMovementList = stockMovementService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(stockMovementList);
    }
}
