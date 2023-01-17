package org.example.exception.stockmovement;

public class InvalidItemStockMovementException extends StockMovementException {

    public InvalidItemStockMovementException(String message) {
        super(message);
    }
}
