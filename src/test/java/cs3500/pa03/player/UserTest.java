package cs3500.pa03.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.game.GameResult;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa03.model.player.User;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa03.viewer.TerminalView;
import cs3500.pa03.viewer.Viewer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests user class
 */
class UserTest {

  private PlayerExtend user;
  private StringBuilder output;

  /**
   * Setups each test
   */
  @BeforeEach
  void setup() {
    String allPossible = "4 4\n"
        + "6 5\n5 4\n"
        + "0 7\n4 2\n6 6\n0 5\n"
        + "0 7\n"
        + "7 5\n"
        + "3 0\n"
        + "0 5\n"
        + "1 3\n"
        + "1 2\n"
        + "2 2\n"
        + "5 3\n"
        + "2 3\n"
        + "7 7\n"
        + "4 4\n"
        + "0 5\n"
        + "4 2\n"
        + "3 1\n"
        + "1 3\n";

    output = new StringBuilder();
    Readable input = new InputStreamReader(new ByteArrayInputStream(allPossible.getBytes()));
    Viewer viewer = new TerminalView(output, input);
    user = new User("Test", viewer);
    user.setRandom(new Random(1));
  }

  /**
   * Tests name function
   */
  @Test
  void nameTest() {
    assertEquals("Test", user.name());
  }

  /**
   * Tests setup function
   */
  @Test
  void setupTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    assertEquals(71, user.toString().length());
  }

  /**
   * Test take shots function
   */
  @Test
  void takeShotsTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    try {
      user.takeShots();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(output.toString().contains("Please Enter 4 Shots"));
  }

  /**
   * Tests report damage function
   */
  @Test
  void reportDamageTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    List<Coord> coords = new ArrayList<>();
    Coord coord = new Coord(1, 1);
    coords.add(coord);
    coord = new Coord(2, 1);
    coords.add(coord);
    coord = new Coord(2, 3);
    coords.add(coord);
    user.reportDamage(coords);
  }

  /**
   * Tests successful hits function
   */
  @Test
  void successfulHitsTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    List<Coord> coords = new ArrayList<>();
    Coord coord = new Coord(1, 1);
    coords.add(coord);
    coord = new Coord(2, 1);
    coords.add(coord);
    coord = new Coord(2, 3);
    coords.add(coord);
    user.successfulHits(coords);
    assertTrue(user.opponentToString().contains("H"));
  }

  /**
   * Tests for end game function
   */
  @Test
  void endGameTest() {
    output.setLength(0);
    try {
      user.endGame(GameResult.DRAW, "draw");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("Game was a draw!\n"
        + "draw".trim(), output.toString().trim());
  }

  /**
   * Tests get board function
   */
  @Test
  void getBoardTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    String result = Arrays.deepToString(user.getBoard());
    assertEquals(120, result.length());
  }

  /**
   * Tests for opponent board function
   */
  @Test
  void getOpponentBoardTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    assertEquals("[[0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], "
           + "[0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]]",
        Arrays.deepToString(user.getOpponentBoard()));
  }

  /**
   * Tests for getFleetSize function
   */
  @Test
  void getFleetSizeTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    assertEquals(4, user.getFleetSize());
  }

  /**
   * Tests toString function
   */
  @Test
  void testToStringTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    String actual = user.toString();
    assertTrue(actual.contains("S"));
    assertTrue(actual.contains("C"));
    assertTrue(actual.contains("B"));
    assertTrue(actual.contains("D"));
  }

  /**
   * Tests opponent toString function
   */
  @Test
  void opponentToStringTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    user.setup(6, 6, specifications);
    assertEquals("0 0 0 0 0 0\n"
        + "0 0 0 0 0 0\n"
        + "0 0 0 0 0 0\n"
        + "0 0 0 0 0 0\n"
        + "0 0 0 0 0 0\n"
        + "0 0 0 0 0 0", user.opponentToString());
  }

  /**
   * Tests set random function
   */
  @Test
  void setRandomTest() {
    user.setRandom(new Random(1));
  }
}