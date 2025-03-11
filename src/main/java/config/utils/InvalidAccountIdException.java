package config.utils;

public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException(String errorMessage) {
        super(errorMessage);
    }
}
