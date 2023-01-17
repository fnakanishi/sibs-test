package org.example.exception.order;

public class InvalidQuantityException extends OrderException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}
