package cs3500.pa03.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.game.BoardGen;
import cs3500.pa03.model.ships.ShipType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BoardGen class
 */
class BoardGenTest {

  private BoardGen genOne;
  private BoardGen genTwo;

  /**
   * Setups each test
   */
  @BeforeEach
  void setup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    genOne = new BoardGen(6, 6, specifications, new Random(1));

    specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 4);
    specifications.put(ShipType.Battleship, 6);
    specifications.put(ShipType.Destroyer, 2);
    specifications.put(ShipType.Submarine, 1);
    genTwo = new BoardGen(13, 13, specifications, new Random(3));
  }

  /**
   * Tests the createBoardTest function
   */
  @Test
  void createBoardTest() {
    genOne.createBoard();
    genTwo.createBoard();
    String actual = Arrays.deepToString(genOne.getBoard());
    assertEquals(120, actual.length());
  }

  /**
   * Tests the createEmptyBoard function
   */
  @Test
  void createEmptyBoardTest() {
    String expected = "[[0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], "
        + "[0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], "
        + "[0, 0, 0, 0, 0, 0], "
        + "[0, 0, 0, 0, 0, 0]]";
    assertEquals(expected, Arrays.deepToString(genOne.createEmptyBoard()));
  }

  /**
   * Tests the countFleetSize function
   */
  @Test
  void countFleetSizeTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    assertEquals(4, genOne.countFleetSize(specifications));
  }

  /**
   * Tests the countFleetSize throws function
   */
  @Test
  void countFleetSizeThrowsTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 0);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    assertThrows(IllegalArgumentException.class,
        () -> genOne.countFleetSize(specifications));
  }

  /**
   * Tests the getBoard test
   */
  @Test
  void getBoardTest() {
    genOne.createBoard();
    String actual = Arrays.deepToString(genOne.getBoard());
    assertEquals(120, actual.length());
  }

  /**
   * Tests the getCoords function
   */
  @Test
  void getCoordsTest() {
    genOne.createBoard();
    String actual = genOne.getCoords().toString();
    assertEquals(540, actual.length());
  }

  /**
   * Tests the createBoard throws invalid size correctly
   */
  @Test
  void createBoardThrowsInvalidBoardSizeTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 2);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    BoardGen genTestThrowsOne = new BoardGen(3, 3, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsOne.createBoard());
    BoardGen genTestThrowsTwo = new BoardGen(4, 6, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsTwo.createBoard());
    BoardGen genTestThrowsFour = new BoardGen(6, 4, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsFour.createBoard());
    BoardGen genTestThrowsFive = new BoardGen(4, 4, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsFive.createBoard());
    BoardGen genTestThrowsSix = new BoardGen(17, 3, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsSix.createBoard());
    BoardGen genTestThrowsSeven = new BoardGen(3, 17, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsSeven.createBoard());
  }

  /**
   * Tests the createBoard throws for invalid fleet size correctly
   */
  @Test
  void createBoardThrowsInvalidFleetSizeTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 1);
    specifications.put(ShipType.Battleship, 0);
    specifications.put(ShipType.Destroyer, 0);
    specifications.put(ShipType.Submarine, 1);
    BoardGen genTestThrowsThree = new BoardGen(6, 6, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsThree.createBoard());

    specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, 10);
    specifications.put(ShipType.Battleship, 1);
    specifications.put(ShipType.Destroyer, 1);
    specifications.put(ShipType.Submarine, 1);
    BoardGen genTestThrowsFour = new BoardGen(6, 6, specifications, new Random(1));
    assertThrows(IllegalArgumentException.class,
        () -> genTestThrowsFour.createBoard());
  }
}