package cs3500.pa03.model.ships;

import cs3500.pa03.model.coords.Coord;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the basic details for each Battleship.
 */
public class ShipImpl implements Ship {

  private final ShipType ship;
  private final String firstLetter;
  private final List<Coord> coords;
  private int currentSize;
  private String direction;

  /**
   * Setups the battleship class.
   *
   * @param ship the ship type
   */
  public ShipImpl(ShipType ship) {
    this.ship = ship;
    this.currentSize = ship.getSize();
    this.firstLetter = ship.getFirstLetter();
    this.coords = new ArrayList<>();
  }

  /**
   * Gets the name of the ship.
   *
   * @return the name of the ship
   */
  public String getName() {
    return ship.getName();
  }

  /**
   * Gets the original size of the ship.
   *
   * @return the original size of the ship
   */
  public int getOriginalSize() {
    return ship.getSize();
  }

  /**
   * Gets the current size of the ship.
   *
   * @return the current size of the ship,
   *          same as original size if the ship has not been hit.
   */
  public int getCurrentSize() {
    return currentSize;
  }

  /**
   * Gets the first letter of the ship name
   *
   * @return first letter of the ship name
   */
  public String getFirstLetter() {
    return firstLetter;
  }

  /**
   * Get ship type
   *
   * @return the ship type
   */
  public ShipType getShipType() {
    return ship;
  }

  /**
   * Get ship position
   *
   * @return the ship position
   */
  @Override
  public List<Coord> getPositions() {
    return coords;
  }

  /**
   * Adds a coord that the ship is currently in.
   *
   * @param coord the coord that the ship is currently in.
   */
  public void addCoord(Coord coord) {
    this.coords.add(coord);
  }

  /**
   * Ship was hit.
   */
  public void shipHit() {
    currentSize--;
  }

  /**
   * Check if ship has been hit yet.
   *
   * @return true if ship was hit, false otherwise
   */
  public boolean checkShipHit() {
    return currentSize != ship.getSize();
  }

  /**
   * Checks if the ship is still in water.
   *
   * @return true if ship is still in water, false otherwise.
   */
  public boolean shipInWater() {
    return currentSize > 0;
  }

  /**
   * Gets the starting coord of the ship.
   *
   * @return the starting coord
   */
  public Coord startingCoord() {
    Coord current = new Coord(0, 0);
    int x = Integer.MAX_VALUE;
    int y = Integer.MAX_VALUE;
    if (shipDirection().equals("VERTICAL")) {
      for (Coord coord : coords) {
        if (coord.getAxisY() < y) {
          y = coord.getAxisY();
          current = coord;
        }
      }
    } else {
      for (Coord coord : coords) {
        if (coord.getAxisX() < x) {
          x = coord.getAxisX();
          current = coord;
        }
      }
    }
    return current;
  }

  /**
   * Returns ship position if it is VERTICAL or HORIZONTAL
   *
   * @return the string of the position
   */
  public String shipDirection() {
    return direction;
  }

  /**
   * Set ship direction.
   *
   * @param direction the direction of ship.
   */
  public void setShipDirection(String direction) {
    this.direction = direction;
  }
}

