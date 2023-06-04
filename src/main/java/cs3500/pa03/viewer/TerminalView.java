package cs3500.pa03.viewer;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * A terminal viewer
 */
public class TerminalView implements Viewer {

  private final Scanner scanner;
  private final Appendable appendable;

  /**
   * Starts the user's viewer
   *
   * @param appendable the appendable for the user
   * @param readable the readable for the user
   */
  public TerminalView(Appendable appendable, Readable readable) {
    this.scanner = new Scanner(Objects.requireNonNull(readable));
    this.appendable = Objects.requireNonNull(appendable);
  }

  /**
   * Handles the question.
   *
   * @param question a string of the question
   * @return a string from the response
   * @throws IOException when failed to read text
   */
  public String askUser(String question) throws IOException {
    appendable.append('\n').append(question.trim()).append('\n');
    String result = null;
    try {
      result = scanner.nextLine();
    } catch (NoSuchElementException e) {
      System.err.println("The response was not found.");
    }
    return result;
  }

  /**
   * Send a user a message
   *
   * @param message the message for user
   * @throws IOException when failed to read the text
   */
  public void sendMessage(String message) throws IOException {
    appendable.append('\n').append('\n').append(message.trim());
  }

}
