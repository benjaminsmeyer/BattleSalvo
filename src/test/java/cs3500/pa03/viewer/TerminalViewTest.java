package cs3500.pa03.viewer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the TerminalView class
 */
class TerminalViewTest {

  private Viewer user;
  private StringBuilder output;

  /**
   * Runs before each test
   */
  @BeforeEach
  void setup() {
    output = new StringBuilder();
    Readable input = new InputStreamReader(System.in);
    user = new TerminalView(output, input);
  }

  /**
   * Tests if the user returns the expected response
   */
  @Test
  void askUserTest() {
    String input = "test";
    output = new StringBuilder();
    Readable inputTest = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));
    Viewer userInput = new TerminalView(output, inputTest);
    String actual = "";
    try {
      actual = userInput.askUser("hello");
    } catch (IOException e) {
      System.err.println("Unable to test.");
    }
    assertEquals(input, actual);
  }

  /**
   * Tests if the user returns null if pressed return
   */
  @Test
  void askUserReturnsNullTest() {
    String userInput = "";

    ByteArrayInputStream response = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(response);
    String actual = "";

    try {
      actual = user.askUser("hello");
    } catch (IOException e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream message = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(message);
    System.setOut(printStream);

    assertNull(actual);
  }

  /**
   * Tests if the user sends expected message
   */
  @Test
  void sendMessageTest() {
    try {
      user.sendMessage("MESSAGE SENT");
      assertEquals("MESSAGE SENT", output.toString().trim());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}