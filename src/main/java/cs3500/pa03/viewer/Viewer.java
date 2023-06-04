package cs3500.pa03.viewer;

import java.io.IOException;

/**
 * An interface for the viewer
 */
public interface Viewer {
  /**
   * Sends a message to the user
   *
   * @param message the string to send
   * @throws IOException when fail to send data
   */
  void sendMessage(String message) throws IOException;

  /**
   * Gets input from user
   *
   * @param question the question
   * @return the input
   * @throws IOException when fail to send data
   */
  String askUser(String question) throws IOException;
}
