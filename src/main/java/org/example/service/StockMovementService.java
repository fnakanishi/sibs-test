package org.example.service;

import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.StockMovement;
import org.example.exception.stockmovement.InvalidItemStockMovementException;
import org.example.exception.stockmovement.InvalidQuantityStockMovementException;
import org.example.exception.stockmovement.StockMovementException;
import org.example.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementService {

    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;
    @Autowired
    EmailService emailService;
    @Autowired
    StockMovementRepository repository;

    private StockMovement save(StockMovement stockMovement) {
        return repository.save(stockMovement);
    }

    public StockMovement create(Long itemId, int quantity) throws StockMovementException {
        Optional<Item> itemOpt = itemService.findById(itemId);

        if (!itemOpt.isPresent()) {
            throw new InvalidItemStockMovementException("Error: Item could not be found!");
        }
        if (quantity <= 0) {
            throw new InvalidQuantityStockMovementException("Error: Invalid item quantity!");
        }

        StockMovement stockMovement = new StockMovement();
        stockMovement.setItem(itemOpt.get());
        stockMovement.setQuantity(quantity);
        stockMovement.setCreationDate(new Date());
        return save(stockMovement);
    }

    public Optional<StockMovement> findById(Long id) {
        return repository.findById(id);
    }

    public StockMovement update(StockMovement stockMovement, Order order) throws StockMovementException {
        return update(stockMovement, order, null);
    }

    public StockMovement update(StockMovement stockMovement, int quantity) throws StockMovementException {
        return update(stockMovement, null, quantity);
    }

    public StockMovement update(StockMovement stockMovement, Order order, Integer quantity) throws StockMovementException {
        if (order != null) {
            stockMovement.setOrder(order);
        }
        if (quantity != null && quantity > 0) {
            stockMovement.setQuantity(quantity);
        } else if (quantity != null) {
            throw new InvalidQuantityStockMovementException("Error: Invalid item quantity!");
        }

        return save(stockMovement);
    }

    public void delete(StockMovement stockMovement) {
        repository.delete(stockMovement);
    }

    public List<StockMovement> findAll() {
        return repository.findAll();
    }

    public Optional<StockMovement> findByItemAndQuantitiy(Item item, int quantity) {
        return repository.findFirstByItemAndQuantityAndOrderIsNullOrderByCreationDate(item, quantity);
    }

    @Transactional
    public void resolveStockMovement(StockMovement stockMovement) {
        Item item = stockMovement.getItem();
        int quantity = stockMovement.getQuantity();
        Optional<Order> orderOptional = orderService.findByItemAndQuantitiy(item, quantity);

        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            stockMovement.setOrder(order);
            emailService.buildOrderEmail(order);
            save(stockMovement);
        }
    }
}
