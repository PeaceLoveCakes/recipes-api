package ru.klingenberg.resipesapi.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized request. Make sure you have a valid token");
    }

}
