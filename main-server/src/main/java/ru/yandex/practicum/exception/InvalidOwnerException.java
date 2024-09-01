package ru.yandex.practicum.exception;

public class InvalidOwnerException extends RuntimeException {
    public InvalidOwnerException(String message) {
        super(message);
    }
}
