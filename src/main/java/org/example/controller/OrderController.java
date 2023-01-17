package org.example.controller;

import org.example.controller.request.OrderRequest;
import org.example.controller.response.MessageResponse;
import org.example.entity.Order;
import org.example.exception.order.OrderException;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody OrderRequest request) {
        Long itemId = request.getItemId();
        Long userId = request.getUserId();
        int quantity = request.getQuantity();

        try {
            Order order = orderService.create(itemId, userId, quantity);
            orderService.resolveOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.findById(id);
        if (!orderOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Order could not be found!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderOpt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody int quantity) {
        Optional<Order> orderOpt = orderService.findById(id);
        if (!orderOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Order could not be found!"));
        }

        try {
            Order order = orderService.update(orderOpt.get(), quantity);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.findById(id);
        if (!orderOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Order could not be found!"));
        }

        orderService.delete(orderOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list-all")
    public ResponseEntity listAll() {
        List<Order> orderList = orderService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }
}
