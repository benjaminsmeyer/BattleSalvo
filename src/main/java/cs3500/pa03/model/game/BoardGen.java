package cs3500.pa03.model.game;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.coords.Coords;
import cs3500.pa03.model.coords.Direction;
import cs3500.pa03.model.ships.Battleship;
import cs3500.pa03.model.ships.Carrier;
import cs3500.pa03.model.ships.Destroyer;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa03.model.ships.Submarine;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Creates the board
 */
public class BoardGen {

  private String[][] board;
  private final int height;
  private final int width;
  private final Map<ShipType, Integer> specifications;
  private final Random random;
  private Map<String, Coord> coords;
  private static final String EMPTY = "0";
  private static final int MIN_FLEET_SIZE = 4;
  private static final int MIN_BOARD_SIZE = 6;
  private static final int MAX_BOARD_SIZE = 15;

  /**
   * Initializes the game board.
   *
   * @param height the height of the game board
   * @param width the width of the game board
   * @param specifications the specifications of the game
   * @param random the random seed used for randomizing the placement of the board
   */
  public BoardGen(int height, int width, Map<ShipType, Integer> specifications, Random random) {
    this.height = height;
    this.width = width;
    this.specifications = specifications;
    this.random = random;
  }

  /**
   * Creates the board for the player.
   */
  public void createBoard() {
    if (!validBoardSize(height, width)) {
      String message =
          String.format(
              "Board dimensions are invalid. "
                  + "It must be greater than or equal to %d and"
                  + " less than or equal to %d.",
              MIN_BOARD_SIZE, MAX_BOARD_SIZE);
      throw new IllegalArgumentException(message);
    } else  if (!validFleetSize(height, width, specifications)) {
      String message = String.format("Fleet size was invalid. It must be greater "
              + "than or equal to %d and less than or equal to %d.",
          MIN_FLEET_SIZE, Math.min(height, width));
      throw new IllegalArgumentException(message);
    }

    board = new String[height][width];
    coords = new HashMap<>();

    for (ShipType ship : specifications.keySet()) {
      for (int i = 0; i < specifications.get(ship); i++) {
        place(ship);
      }
    }
    setRest();
  }

  /**
   * Creates an empty board.
   *
   * @return a list of an empty board.
   */
  public String[][] createEmptyBoard() {
    String[][] emptyBoard = new String[height][width];
    for (String[] row : emptyBoard) {
      Arrays.fill(row, EMPTY);
    }
    return emptyBoard;
  }

  /**
   * Checks if it is a valid fleet size.
   *
   * @param height the height
   * @param width the width
   * @param specifications the specifications
   * @return true if the fleet size is valid, false otherwise.
   */
  private boolean validFleetSize(int height, int width, Map<ShipType, Integer> specifications) {
    int count = countFleetSize(specifications);
    boolean countMinSize = count >= MIN_FLEET_SIZE;
    boolean countMaxSize = count <= Math.min(height, width);
    return countMinSize && countMaxSize;
  }

  /**
   * Counts the fleet size.
   *
   * @param specifications the specifications for the board.
   * @return a count of the fleet size
   * @throws IllegalArgumentException when the fleet size is invalid.
   */
  public int countFleetSize(Map<ShipType, Integer> specifications) {
    int count = 0;
    for (ShipType ship : specifications.keySet()) {
      count += specifications.get(ship);
      if (specifications.get(ship) < 1) {
        throw new IllegalArgumentException("Invalid fleet size. "
            + "There must be at least a ship of each type.");
      }
    }
    return count;
  }

  /**
   * Checks if the board size is valid
   *
   * @param height the height
   * @param width the width
   * @return true if the board size is valid, false otherwise.
   */
  private boolean validBoardSize(int height, int width) {
    boolean validMinHeight = height >=  MIN_BOARD_SIZE;
    boolean validMaxHeight = height <= MAX_BOARD_SIZE;
    boolean validMinWidth = width >=  MIN_BOARD_SIZE;
    boolean validMaxWidth = width <= MAX_BOARD_SIZE;
    boolean validHeight = validMinHeight && validMaxHeight;
    boolean validWidth = validMinWidth && validMaxWidth;
    return validHeight && validWidth;
  }

  /**
   * Gets the board.
   *
   * @return the board.
   */
  public String[][] getBoard() {
    return board;
  }

  /**
   * Gets the coords.
   *
   * @return a map of the coords
   */
  public Map<String, Coord> getCoords() {
    return coords;
  }

  /**
   * Sets the rest of the empty spots on the board.
   */
  private void setRest() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (board[i][j] == null) {
          setCoords(j, i, EMPTY);
        }
      }
    }
  }

  /**
   * Place the ship onto the board.
   *
   * @param ship the ship of the spot
   */
  private void place(ShipType ship) {
    int[] spots;
    try {
      spots = findSpots(ship.getSize());
      setBoard(spots, ship);
    } catch (IllegalStateException e) {
      createBoard();
    }
  }

  /**
   * Sets the board with the valid spots.
   *
   * @param spots the valid spots for the ship
   * @param ship the ship of the spot
   */
  private void setBoard(int[] spots, ShipType ship) {
    String label = ship.getFirstLetter();
    Ship battleShip = getShipType(ship);
    int row = spots[0];
    int column = spots[1];
    int direction = spots[2];
    for (int i = 0; i < ship.getSize(); i++) {
      if (direction == Direction.UP.getDirection()) {
        setCoords(row, column - i, label, battleShip);
      } else if (direction == Direction.DOWN.getDirection()) {
        setCoords(row, column + i, label, battleShip);
      } else if (direction == Direction.RIGHT.getDirection()) {
        setCoords(row + i, column, label, battleShip);
      } else {
        setCoords(row - i, column, label, battleShip);
      }
    }
  }

  /**
   * Sets the label for the x and y spot of the board.
   *
   * @param x the x spot
   * @param y the y spot
   * @param label the label for the board
   * @param battleShip the ship that takes the spot
   */
  private void setCoords(int x, int y, String label, Ship battleShip) {
    board[y][x] = label;
    Coord location = new Coords(x, y, battleShip);
    battleShip.addCoord(location);
    coords.put(location.toString(), location);
  }

  /**
   * Sets the label for the x and y spot of the board.
   *
   * @param x the x spot
   * @param y the y spot
   * @param label the label for the board
   */
  private void setCoords(int x, int y, String label) {
    board[y][x] = label;
    Coord location = new Coords(x, y);
    coords.put(location.toString(), location);
  }

  /**
   * Gets valid spots for the ship.
   *
   * @param size the size of the ship.
   * @return a list of valid spots for ship.
   * @throws IllegalStateException when unable to find spots for ship
   */
  private int[] findSpots(int size) {
    int row;
    int column;
    int direction;
    int tries = 0;
    int maxSize = Math.max(width, height);
    int maxTries = (int) Math.pow(maxSize, 2);
    do {
      if (tries > maxTries) {
        throw new IllegalStateException("Unable to find spots for ship.");
      }
      row = random.nextInt(0, width);
      column = random.nextInt(0, height);
      direction = random.nextInt(0, Direction.LEFT.getDirection() + 1);
      tries++;
    } while (!validSpots(row, column, direction, size));
    return new int[]{row, column, direction};
  }

  /**
   * Checks for valid spots for the ship.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @param direction the direction for the ship
   * @param size the size of the ship.
   * @return true if there are valid spots, false otherwise.
   */
  private boolean validSpots(int row, int column, int direction, int size) {
    if (direction == Direction.UP.getDirection()) {
      return availableUpSpots(row, column, size);
    } else if (direction == Direction.DOWN.getDirection()) {
      return availableDownSpots(row, column, size);
    } else if (direction == Direction.RIGHT.getDirection()) {
      return availableRightSpots(row, column, size);
    } else {
      return availableLeftSpots(row, column, size);
    }
  }

  /**
   * Gets the class type of the ship
   *
   * @param ship the ship type
   * @return the ship type class
   * @throws IllegalArgumentException when the ship type does not exist as a class
   */
  private Ship getShipType(ShipType ship) {
    if (ship == ShipType.Submarine) {
      return new Submarine();
    } else if (ship == ShipType.Battleship) {
      return new Battleship();
    } else if (ship == ShipType.Carrier) {
      return new Carrier();
    } else {
      return new Destroyer();
    }
  }

  /**
   * Checks if the spot is available for the ship upwards.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @param size the size of the ship
   * @return true if spot is available for ship, false otherwise.
   */
  private boolean availableUpSpots(int row, int column, int size) {
    for (int i = 0; i < size; i++) {
      if (!spotAvailable(column - i, row)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the spot is available for the ship downwards.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @param size the size of the ship
   * @return true if spot is available for ship, false otherwise.
   */
  private boolean availableDownSpots(int row, int column, int size) {
    for (int i = 0; i < size; i++) {
      if (!spotAvailable(column + i, row)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the spot is available for the ship.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @return true if spot is available for ship, false otherwise.
   */
  private boolean spotAvailable(int column, int row) {
    try {
      return board[column][row] == null;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Checks if the spot is available for the ship on the right.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @param size the size of the ship
   * @return true if spot is available for ship, false otherwise.
   */
  private boolean availableRightSpots(int row, int column, int size) {
    for (int i = 0; i < size; i++) {
      if (!spotAvailable(column, row + i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the spot is available for the ship on the left.
   *
   * @param row the row for the ship
   * @param column the column for the ship
   * @param size the size of the ship
   * @return true if spot is available for ship, false otherwise.
   */
  private boolean availableLeftSpots(int row, int column, int size) {
    for (int i = 0; i < size; i++) {
      if (!spotAvailable(column, row - i)) {
        return false;
      }
    }
    return true;
  }

}