package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests controller class
 */
class ControllerTest {

  /**
   * Tests a game where player wins
   */
  @Test
  void runGamePlayerTest() {
    String startText = "1 1\n"
        + "e 5\n"
        + "9 9\n"
        + "6\n"
        + "6\n"
        + "1 1 0 1\n"
        + "1 1 1 1e\n"
        + "1 1 1 233\n"
        + "3 3 3 3\n"
        + "3 1 2 1\n";

    System.out.println("runGamePlayerTest");

    String restText = allPossibleTestString();

    Appendable appendable = new StringBuilder();
    Readable input = new StringReader(startText + restText + "\nexit\n");
    TerminalController controller = new TerminalController(appendable, input, new Random());
    System.out.println("Before Run Game Method Call");
    controller.runGame();
    System.out.println("After Run Game Method Call");
    String actual = appendable.toString();
    assertTrue(actual.contains("Player's Board"));
    assertTrue(actual.contains("Opponent's Board"));
    assertTrue(actual.contains("Uh Oh! You've entered invalid fleet sizes."));
    assertTrue(actual.contains(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."));
    assertTrue(actual.contains("of the game must be in the range (6, 15), inclusive."));
    assertTrue(actual.contains("Hello! Welcome to the OOD BattleSalvo Game!"));

    System.out.println("runGamePlayerTestSuccess");
  }

  /**
   * Tests a game where player exits
   */
  @Test
  void runGamePlayerExitsTest() {
    String startText = """
        1 1
        e 5
        9 10
        6
        6
        1 1 0 1
        1 1 1 1e
        1 1 1 233
        3 3 3 3
        3 1 2 1\n""";

    System.out.println("runGamePlayerExitsTest");

    String restText = allPossibleTestString().substring(0, 10) + "\nexit\n";

    Appendable appendable = new StringBuilder();
    Readable input = new StringReader(startText + restText);
    TerminalController controller = new TerminalController(appendable, input, new Random());
    controller.runGame();

    System.out.println("runGamePlayerExitsTestSuccess");
  }

  /**
   * String to test
   *
   * @return the testing string
   */
  private String allPossibleTestString() {
    String allPossible = """
        4 4
        6 5
        5 4
        0 7
        4 2
        6 6
        0 5
        0 7
        7 5
        3 0
        0 5
        1 3
        1 2
        2 2
        5 3
        2 3
        7 7
        4 4
        0 5
        4 2
        3 1
        1 3
        3 3
        4 1
        5 0
        5 4
        1 0
        2 1
        2 0
        0 2
        5 3
        4 4
        7 7
        5 5
        6 1
        2 6
        1 0
        7 7
        3 1
        2 2
        0 0
        5 1
        2 1
        4 4
        0 5
        4 6
        6 1
        0 2
        0 1
        3 2
        4 7
        2 4
        1 0
        5 1
        4 1
        6 3
        1 7
        4 4
        4 0
        1 5
        1 0
        7 1
        7 1
        7 2
        4 4
        0 4
        4 5
        2 0
        5 6
        1 4
        5 6
        4 1
        5 3
        0 7
        1 3
        0 1
        6 6
        7 7
        0 1
        0 6
        6 5
        2 3
        4 0
        5 0
        7 0
        3 1
        0 7
        4 6
        2 3
        2 4
        1 2
        7 1
        3 6
        2 6
        4 5
        1 0
        0 3
        3 0
        4 1
        3 1
        1 0
        3 0
        4 1
        6 0
        3 0
        5 1
        5 3
        6 0
        2 1
        5 7
        1 5
        3 3
        7 2
        1 3
        2 4
        5 3
        7 0
        2 1
        4 4
        3 5
        6 4
        5 4
        3 1
        0 3
        2 4
        1 1
        4 4
        3 3
        6 2
        1 7
        1 1
        1 7
        5 1
        6 5
        5 2
        0 2
        6 1
        2 2
        0 7
        1 0
        2 1
        3 0
        4 5
        5 7
        5 5
        5 1
        7 1
        5 4
        2 3
        0 1
        1 3
        0 4
        7 7
        2 7
        0 1
        4 3
        3 3
        5 7
        6 2
        2 7
        4 4
        6 3
        0 4
        6 4
        4 0
        4 1
        7 1
        3 4
        6 7
        4 6
        0 1
        6 1
        7 7
        6 0
        7 1
        5 0
        3 1
        4 0
        2 5
        5 6
        5 7
        3 1
        7 1
        5 7
        2 4
        0 0
        6 4
        6 6
        2 2
        2 1
        7 4
        1 4
        2 5
        6 1
        5 2
        6 4
        6 7
        7 4
        0 5
        7 0
        6 7
        1 3
        1 6
        0 3
        5 3
        6 3
        3 3
        4 2
        7 6
        6 6
        5 2
        2 3
        3 0
        4 6
        5 1
        5 7
        7 4
        3 2
        3 3
        4 3
        3 5
        4 4
        0 4
        6 6
        6 5
        1 3
        1 1
        7 3
        5 3
        6 0
        6 7
        2 0
        2 6
        2 7
        5 6
        2 2
        0 4
        4 1
        4 3
        7 3
        5 7
        3 6
        1 7
        2 5
        2 5
        6 7
        6 2
        5 5
        4 3
        4 3
        4 5
        7 3
        4 7
        4 1
        1 0
        1 7
        0 3
        2 6
        5 2
        2 0
        3 5
        2 1
        7 7
        0 1
        2 4
        1 0
        0 3
        3 4
        6 6
        1 3
        0 1
        6 4
        4 4
        6 6
        4 4
        7 7
        0 4
        7 4
        2 2
        7 4
        4 6
        0 5
        3 2
        5 5
        2 4
        4 6
        0 0
        7 7
        2 3
        3 2
        0 6
        7 5
        4 0
        0 7
        3 2
        7 3
        4 6
        3 6
        3 3
        4 6
        0 6
        1 0
        4 7
        5 6
        6 7
        1 5
        2 6
        7 6
        5 6
        0 4
        7 0
        4 0
        7 6
        6 6
        1 3
        1 6
        5 5
        6 6
        0 2
        1 5
        1 0
        1 0
        7 5
        0 0
        2 2
        1 4
        0 3
        3 0
        3 5
        5 1
        7 2
        1 4
        1 2
        6 4
        6 6
        1 5
        4 1
        3 1
        2 3
        6 5
        4 0
        7 7
        7 5
        0 3
        7 3
        5 2
        0 0
        3 3
        2 2
        7 6
        6 2
        7 4
        1 6
        5 4
        1 6
        3 5
        3 1
        6 1
        0 1
        5 0
        6 7
        7 1
        6 1
        6 3
        2 2
        0 1
        1 0
        5 1
        7 4
        7 6
        4 2
        6 1
        6 3
        6 4
        3 4
        0 2
        7 5
        6 0
        1 6
        1 0
        0 1
        7 6
        7 5
        6 4
        0 4
        2 1
        1 2
        0 6
        0 6
        1 0
        5 4
        4 0
        4 5
        7 0
        4 4
        3 2
        2 3
        1 2
        6 0
        2 3
        1 7
        0 7
        7 4
        6 1
        6 2
        1 2
        1 3
        6 4
        2 6
        4 6
        5 7
        3 0
        5 4
        6 4
        3 4
        3 0
        0 6
        4 0
        4 5
        1 7
        3 6
        3 0
        6 1
        0 6
        2 6
        5 6
        7 7
        6 4
        5 6
        3 3
        4 3
        4 4
        4 6
        6 7
        1 4
        4 4
        4 3
        6 6
        5 1
        6 2
        5 1
        4 7
        1 6
        4 0
        1 0
        4 3
        6 1
        7 7
        5 4
        1 1
        7 3
        3 0
        5 0
        2 6
        2 2
        6 2
        7 7
        0 7
        4 4
        3 3
        3 0
        1 3
        4 1
        0 0
        6 1
        0 6
        0 5
        4 2
        6 2
        2 7
        7 6
        1 7
        5 4
        0 4
        4 2
        5 4
        3 5
        5 4
        3 7
        7 5
        2 3
        7 7
        1 4
        5 1
        3 3
        6 0
        1 5
        7 6
        0 0
        5 3
        2 0
        0 6
        4 0
        7 2
        7 6
        2 4
        7 4
        7 6
        4 5
        7 3""";
    return allPossible;
  }
}