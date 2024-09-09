package ru.yandex.practicum.exception;

public class EventNotEditableException extends RuntimeException {
    public EventNotEditableException(String message) {
        super(message);
    }
}
