package cs3500.pa03.model.player;

import cs3500.pa03.model.coords.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AI class against player
 */
public class ArtificialIntelligence extends PlayerImpl {
  private final List<Coord> targets;
  private final List<Coord> hits;
  private final List<Coord> allShots;

  /**
   * Setups the player
   *
   * @param name the name of the player
   */
  public ArtificialIntelligence(String name) {
    super(name);

    targets = new ArrayList<>();
    hits = new ArrayList<>();
    allShots = new ArrayList<>();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots
   * returned should equal the number of ships on this player's
   * board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int amountAvailable = countAvailableSpots(opponentBoard);
    int count = Math.min(amountAvailable, fleetSize);

    getHits();
    while (count > 0) {
      Coord coord = takeOneShot();
      shots.add(coord);
      allShots.add(coord);
      opponentBoard[coord.getY()][coord.getX()] = MISS;
      count--;
    }

    return shots;
  }

  /**
   * Randomly takes shots on the board.
   *
   * @param currentCount the current available shots left
   * @return the list of randomly placed shots
   */
  private List<Coord> takeShotsRandomly(int currentCount) {
    List<Coord> coords = new ArrayList<>();
    // int amountAvailable = countAvailableSpots(opponentBoard);
    // int count = Math.min(amountAvailable, fleetSize);
    int count = currentCount;
    while (count > 0) {
      int x = random.nextInt(0, width);
      int y = random.nextInt(0, height);
      if (validShot(x, y)) {
        Coord coord = new Coord(x, y);
        coords.add(coord);
        opponentBoard[y][x] = MISS;
        count--;
      }
    }
    return coords;
  }

  /**
   * Returns one shot taken by this player.
   *
   * @return the locations of the shot on the opponent's board
   */
  private Coord takeOneShot() {
    if (!hits.isEmpty()) {
      List<Coord> potentialTargets = findPotentialTargets();

      for (Coord target : potentialTargets) {
        if ((validShot(target.getX(), target.getY()))
            && (!allShots.contains(target))
            && (!targets.contains(target))) {
          targets.add(target);
        }
      }
    }

    if (targets.isEmpty()) {
      return randomGuess();
    } else {
      return targets.remove(0);
    }
  }

  /**
   * Returns one random shot by this player on the opponents board.
   *
   * @return the locations of a shot on the opponent's board
   */
  private Coord randomGuess() {
    Coord coord = null;
    while (coord == null || allShots.contains(coord)) {
      int x = random.nextInt(0, width);
      int y = random.nextInt(0, height);
      if (validShot(x, y)) {
        coord = new Coord(x, y);
      }
    }
    return coord;
  }

  /**
   * Returns a list of potential ship locations on the opponents board
   * based on information gathered from shots.
   *
   * @return the potential locations of ships on the opponent's board
   */
  private List<Coord> findPotentialTargets() {
    List<Coord> potentialTargets = new ArrayList<>();

    for (Coord hit : hits) {
      if (validShot(hit.getX() + 1, hit.getY())) {
        potentialTargets.add(new Coord(hit.getX() + 1, hit.getY()));
      }
      if (validShot(hit.getX(), hit.getY() + 1)) {
        potentialTargets.add(new Coord(hit.getX(), hit.getY() + 1));
      }
      if (validShot(hit.getX() - 1, hit.getY())) {
        potentialTargets.add(new Coord(hit.getX() - 1, hit.getY()));
      }
      if (validShot(hit.getX(), hit.getY() - 1)) {
        potentialTargets.add(new Coord(hit.getX(), hit.getY() - 1));
      }
    }

    return potentialTargets;
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

  /**
   * Checks if the shots was a hit
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the shot was a hit, false otherwise.
   */
  private boolean validHit(int x, int y) {
    try {
      return Objects.equals(opponentBoard[y][x], HIT) ;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Updates player knowledge of locations where players shots have damaged opponents ships.
   */
  private void getHits() {
    for (int x = 0; x < Math.max(height, width); x++) {
      for (int y = 0; y < Math.max(height, width); y++) {
        if (validHit(x, y)) {
          Coord coord = new Coord(x, y);
          if (opponentBoard[y][x].equals(HIT) && !hits.contains(coord)) {
            hits.add(coord);
          }
        }
      }
    }
  }

}
