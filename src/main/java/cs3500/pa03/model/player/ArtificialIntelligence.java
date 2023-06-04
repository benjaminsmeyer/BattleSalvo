package cs3500.pa03.model.player;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.coords.Coords;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AI class against player
 */
public class ArtificialIntelligence extends PlayerImpl {

  /**
   * Setups the player
   *
   * @param name the name of the player
   */
  public ArtificialIntelligence(String name) {
    super(name);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> coords = new ArrayList<>();
    int amountAvailable = countAvailableSpots(opponentBoard);
    int count = Math.min(amountAvailable, fleetSize);
    while (count > 0) {
      int x = random.nextInt(0, width);
      int y = random.nextInt(0, height);
      if (validShot(x, y)) {
        Coord coord = new Coords(x, y);
        coords.add(coord);
        opponentBoard[y][x] = MISS;
        count--;
      }
    }
    return coords;
  }

  /**
   * Counts how many spots are available on board.
   *
   * @return the amount of available spots available on board
   */
  private int countAvailableSpots(String[][] board) {
    int count = 0;
    for (String[] row : board) {
      for (String str : row) {
        if (str.equalsIgnoreCase(EMPTY)) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Checks if the shots are valid
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the shot is valid, false otherwise.
   */
  private boolean validShot(int x, int y) {
    try {
      return Objects.equals(opponentBoard[y][x], EMPTY);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }
}
