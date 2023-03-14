package com.shandrikov.bookshop.exceptions;

public class InvalidNumberBooksException extends RuntimeException{
    public InvalidNumberBooksException(String message) {
        super(message);
    }
}
