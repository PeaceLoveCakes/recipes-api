package ru.klingenberg.resipesapi.exception.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionMessage {

    private final String message;

    public static ExceptionMessage of(String message) {
        return new ExceptionMessage(message);
    }

}
