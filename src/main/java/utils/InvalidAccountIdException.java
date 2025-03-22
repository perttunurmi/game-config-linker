package utils;

/**
 * This will be thrown if accountID is not a whole number
 * InvalidAccountIdException accountsID is not a whole number
 */
public class InvalidAccountIdException extends Exception {
  public InvalidAccountIdException(final String errorMessage) {
    super(errorMessage);
  }
}
