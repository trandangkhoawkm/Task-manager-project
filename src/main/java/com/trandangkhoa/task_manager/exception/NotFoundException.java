package com.trandangkhoa.task_manager.exception;

// This class throw an exception message instead of returning null when a user request an invalid value
public class NotFoundException extends RuntimeException { // RuntimeException doesn't require a throw declaration or try-catch where it is called

    public NotFoundException(String message) {
        super(message);
    }
}