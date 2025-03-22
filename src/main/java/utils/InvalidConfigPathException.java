package utils;

/**
 * InvalidConfigPathException something is wrong with the entered configpath
 */
public class InvalidConfigPathException extends Exception {
  public InvalidConfigPathException(final String errorMessage) {
    super(errorMessage);
  }
}
