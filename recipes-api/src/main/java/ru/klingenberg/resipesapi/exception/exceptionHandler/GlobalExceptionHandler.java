package ru.klingenberg.resipesapi.exception.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.klingenberg.resipesapi.exception.BadRequestException;
import ru.klingenberg.resipesapi.exception.UnauthorizedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionMessage handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ExceptionMessage.of(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionMessage handle(EntityNotFoundException exception) {
        return ExceptionMessage.of(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionMessage handle(BadRequestException exception) {
        return ExceptionMessage.of(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionMessage handle(UnauthorizedException exception) {
        return ExceptionMessage.of(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionMessage handle(MethodArgumentNotValidException exception) {
        return ExceptionMessage.of(exception.getMessage());
    }

}
