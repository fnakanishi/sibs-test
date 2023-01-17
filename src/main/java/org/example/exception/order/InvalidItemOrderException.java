package org.example.exception.order;

public class InvalidItemOrderException extends OrderException {

    public InvalidItemOrderException(String message) {
        super(message);
    }
}
