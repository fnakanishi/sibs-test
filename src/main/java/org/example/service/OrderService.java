package org.example.service;

import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.StockMovement;
import org.example.entity.User;
import org.example.exception.order.*;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired
    StockMovementService stockMovementService;
    @Autowired
    EmailService emailService;
    @Autowired
    OrderRepository repository;

    public Order create(Long userId, Long itemId, int quantity) throws OrderException {
        Optional<Item> itemOpt = itemService.findById(itemId);
        Optional<User> userOpt = userService.findById(userId);

        if (!itemOpt.isPresent()) {
            throw new InvalidItemOrderException("Error: Item could not be found!");
        }
        if (!userOpt.isPresent()) {
            throw new InvalidUserException("Error: User could not be found!");
        }
        if (quantity <= 0) {
            throw new InvalidQuantityException("Error: Invalid item quantity!");
        }

        return save(itemOpt.get(), userOpt.get(), quantity);
    }

    private Order save(Order order) {
        return repository.save(order);
    }

    public Order save(Item item, User user, int quantity) {
        Order order = new Order();
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(quantity);
        order.setCreationDate(new Date());

        return save(order);
    }

    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    public Order update(Order order, int quantity) throws InvalidQuantityException, InvalidIssuedOrderException {
        if (order.getStockMovement() != null) {
            throw new InvalidIssuedOrderException("Error: Cannot update an order that has been issued!");
        }
        if (quantity <= 0) {
            throw new InvalidQuantityException("Error: Invalid item quantity!");
        }
        order.setQuantity(quantity);

        return save(order);
    }

    public void delete(Order order) {
        repository.delete(order);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Optional<Order> findByItemAndQuantitiy(Item item, int quantity) {
        return repository.findFirstByItemAndQuantityAndStockMovementIsNullOrderByCreationDate(item, quantity);
    }

    @Transactional
    public void resolveOrder(Order order) {
        Item item = order.getItem();
        int quantity = order.getQuantity();
        Optional<StockMovement> stockMovementOptional = stockMovementService.findByItemAndQuantitiy(item, quantity);

        if(stockMovementOptional.isPresent()) {
            StockMovement stockMovement = stockMovementOptional.get();
            order.setStockMovement(stockMovement);
            emailService.buildOrderEmail(order);
            save(order);
        }
    }
}
