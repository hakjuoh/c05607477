package edu.project.c05607477.exceptions;

public class InvalidTransactionRequestException extends RuntimeException {

    public InvalidTransactionRequestException() {
    }

    public InvalidTransactionRequestException(String message) {
        super(message);
    }
}
