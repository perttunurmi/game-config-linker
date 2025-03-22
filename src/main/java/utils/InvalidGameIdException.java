package utils;

/**
 * InvalidGameIdException folder for that game could not be found
 */
public class InvalidGameIdException extends Exception {
  public InvalidGameIdException(final String errorMessage) {
    super(errorMessage);
  }
}
