package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException e) {
        log.warn("Ошибка валидации", e);
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.warn("Ошибка на стороне сервера", e);
        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "Integrity constraint has been violated.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        log.warn("Пользователь не найден.", e);
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "User not found.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFoundException(final CategoryNotFoundException e) {
        log.warn("Категория не найдена.", e);
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "Category not found.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEventNotFoundException(final EventNotFoundException e) {
        log.warn("Событие не найдено.", e);
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "Event not found.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFoundException(final RequestNotFoundException e) {
        log.warn("Заявка не найдена.", e);
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "Request not found.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompilationNotFoundException(final CompilationNotFoundException e) {
        log.warn("Подборка не найдена.", e);
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                "Compilation not found.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInvalidEventDateException(final InvalidEventDateException e) {
        log.warn("Неверная дата.", e);
        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "Invalid event date.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleInvalidOwnerException(final InvalidOwnerException e) {
        log.warn("Неверный владелец.", e);
        return new ErrorResponse(
                HttpStatus.FORBIDDEN.name(),
                "Invalid owner.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEventNotEditableException(final EventNotEditableException e) {
        log.warn("Событие не доступно для редактирования.", e);
        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "The event cannot be edited.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleActionNotAllowedException(final ActionNotAllowedException e) {
        log.warn("Действие не разрешено.", e);
        return new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                "Action not allowed.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidDateRangeException(final InvalidDateRangeException e) {
        log.warn("Неправильный диапазон дат.", e);
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                "Invalid date range.",
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleResponseStatusException(final ResponseStatusException e) {
        log.warn("Неправильный статус ответа.", e);
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Invalid response status.",
                e.getMessage(),
                LocalDateTime.now());
    }
}
