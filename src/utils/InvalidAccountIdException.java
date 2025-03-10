package src.utils;

public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException(String errorMessage) {
        super(errorMessage);
    }
}
