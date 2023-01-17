package org.example.exception.order;

public class InvalidIssuedOrderException extends OrderException {

    public InvalidIssuedOrderException(String message) {
        super(message);
    }
}
