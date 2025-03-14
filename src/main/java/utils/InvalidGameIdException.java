package utils;

public class InvalidGameIdException extends Exception {
  public InvalidGameIdException(final String errorMessage) {
    super(errorMessage);
  }
}
