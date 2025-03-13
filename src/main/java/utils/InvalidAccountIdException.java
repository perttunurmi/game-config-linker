package utils;

public class InvalidAccountIdException extends Exception {
  public InvalidAccountIdException(final String errorMessage) {
    super(errorMessage);
  }
}
