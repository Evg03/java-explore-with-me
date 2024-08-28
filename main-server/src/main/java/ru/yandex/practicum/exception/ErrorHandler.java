package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        log.warn("Ошибка на стороне сервера", e);
        return new ErrorResponse(
                "Ошибка на стороне сервера", e.getMessage()
        );
    }*/

    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.warn("Ошибка на стороне сервера", e);
        return new ErrorResponse(
                "Ошибка на стороне сервера", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException e) {
        log.warn("Ошибка валидации", e);
        return new ErrorResponse("Ошибка валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn("Ошибка валидации", e);
        return new ErrorResponse("Ошибка валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnknownStateException(final UnknownStateException e) {
        log.warn("Unknown state: UNSUPPORTED_STATUS", e);
        return new ErrorResponse("Unknown state: UNSUPPORTED_STATUS", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        log.warn("Ошибка. Пользователь не найден", e);
        return new ErrorResponse("Ошибка. Пользователь не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookingNotFoundException(final BookingNotFoundException e) {
        log.warn("Ошибка. Бронирование не найдено", e);
        return new ErrorResponse("Ошибка. Бронирование не найдено", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFoundException(final ItemNotFoundException e) {
        log.warn("Ошибка. Item не найден", e);
        return new ErrorResponse("Ошибка. Item не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIncorrectItemOwnerIdException(final IncorrectItemOwnerIdException e) {
        log.warn("Ошибка. Неправильный id владельца.", e);
        return new ErrorResponse("Ошибка. Неправильный id владельца.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIncorrectBookingOwnerIdException(final IncorrectBookingOwnerIdException e) {
        log.warn("Ошибка. Неправильный id владельца.", e);
        return new ErrorResponse("Ошибка. Неправильный id владельца.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookingAlreadyApprovedException(final BookingStatusAlreadyChangedException e) {
        log.warn("Ошибка. Бронирование уже одобрено.", e);
        return new ErrorResponse("Ошибка. Бронирование уже одобрено.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookingOwnItemException(final BookingOwnItemException e) {
        log.warn("Ошибка. Нельзя забронировать собственный Item.", e);
        return new ErrorResponse("Ошибка. Нельзя забронировать собственный Item.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemNotRentedByUserException(final ItemNotRentedByUserException e) {
        log.warn("Ошибка. Пользователь не брал Item в аренду.", e);
        return new ErrorResponse("Ошибка. Пользователь не брал Item в аренду.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemNotAvailableException(final ItemNotAvailableException e) {
        log.warn("Ошибка. Item не доступен для бронирования.", e);
        return new ErrorResponse("Ошибка. Item не доступен для бронирования.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFoundException(final RequestNotFoundException e) {
        log.warn("Ошибка. Request не найден", e);
        return new ErrorResponse("Ошибка. Request не найден", e.getMessage());
    }*/

}
