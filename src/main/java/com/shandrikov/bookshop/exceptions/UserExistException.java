package com.shandrikov.bookshop.exceptions;

import static com.shandrikov.bookshop.utils.StringPool.USER_EXISTS;

public class UserExistException extends RuntimeException{
    public UserExistException() {
        this(USER_EXISTS);
    }

    public UserExistException(String message) {
        super(message);
    }
}
