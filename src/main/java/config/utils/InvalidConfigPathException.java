package config.utils;

public class InvalidConfigPathException extends Exception {
    public InvalidConfigPathException(String errorMessage) {
        super(errorMessage);
    }
}
