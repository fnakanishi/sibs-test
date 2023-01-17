package org.example.exception.order;

public class InvalidUserException extends OrderException {

    public InvalidUserException(String message) {
        super(message);
    }
}
