package org.example.exception.stockmovement;

public class InvalidQuantityStockMovementException extends StockMovementException {

    public InvalidQuantityStockMovementException(String message) {
        super(message);
    }
}
