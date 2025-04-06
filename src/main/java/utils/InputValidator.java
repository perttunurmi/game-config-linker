package utils;

import java.io.File;

/** Class which contais different methods for validating user input */
public final class InputValidator {

  /**
   * @param accountId Steam accountID. Note: is a number
   * @throws InvalidAccountIdException if AccountID is not a whole number
   */
  public static void validateAccountId(final String accountId) throws InvalidAccountIdException {
    if (!accountId.matches("^[0-9]+")) {
      throw new InvalidAccountIdException("AccountID can only contain numbers");
    }
  }

  /**
   * @param accountId  Steam accountID. Note: is a number
   * @param configpath Path to steam userdata folder
   * @throws InvalidAccountIdException  if AccountID is not a whole numbers
   * @throws InvalidConfigPathException if userdata folder doesn't exist or is not
   *                                    a directory
   */
  public static void validateAccountFolder(final String accountId, final String configpath)
      throws InvalidAccountIdException, InvalidConfigPathException {

    validateAccountId(accountId);
    validateConfigPath(configpath);

    final File accountConfigFolder = new File(configpath, accountId);

    if (!accountConfigFolder.isDirectory()) {
      throw new InvalidAccountIdException(
          "Directory " + accountConfigFolder.getAbsolutePath() + " doesn't exist");
    }
  }

  /**
   * @param accountId  Steam accountID. Note: is a number
   * @param configpath Path to steam userdata folder
   * @throws InvalidAccountIdException  if AccountID is not a whole numbers
   * @throws InvalidConfigPathException if userdata folder doesn't exist or is not
   *                                    a directory
   */
  public static void validateAccountFolder(final String accountId, final File configpath)
      throws InvalidAccountIdException, InvalidConfigPathException {

    validateAccountId(accountId);
    validateConfigPath(configpath);

    final File accountConfigFolder = new File(configpath, accountId);

    if (!accountConfigFolder.isDirectory()) {
      throw new InvalidAccountIdException(
          "Directory " + accountConfigFolder.getAbsolutePath() + " doesn't exist");
    }
  }

  /**
   * @param configPath Path to steam userdata folder
   * @throws InvalidConfigPathException if userdata folder doesn't exist or is not
   *                                    a directory
   */
  public static void validateConfigPath(final String configPath) throws InvalidConfigPathException {
    final File userdata = new File(configPath);
    if (!userdata.isDirectory()) {
      throw new InvalidConfigPathException(
          "Directory " + userdata.getAbsolutePath() + " doesn't exist");
    }
  }

  /**
   * @param configPath Path to steam userdata folder
   * @throws InvalidConfigPathException if userdata folder doesn't exist or is not
   *                                    a directory
   */
  public static void validateConfigPath(final File configPath) throws InvalidConfigPathException {
    final File userdata = configPath;
    if (!userdata.isDirectory()) {
      throw new InvalidConfigPathException(
          "Directory " + userdata.getAbsolutePath() + " doesn't exist");
    }
  }

  /**
   * @param accountPath path to userdata/accountid as a File type
   * @param gameId      Steam gameid
   * @throws InvalidGameIdException folder for that game does not exist
   */
  public static void validateGameId(final File accountPath, final String gameId)
      throws InvalidGameIdException {
    final File gameDir = new File(accountPath, gameId);
    if (!gameDir.isDirectory()) {
      throw new InvalidGameIdException("Directory " + gameDir.getAbsolutePath() + " doesn't exist");
    }
  }
}
