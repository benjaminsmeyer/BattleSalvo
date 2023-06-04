package cs3500.pa03.model.ships;

import cs3500.pa03.model.coords.Coord;
import java.util.List;

/**
 * Interface for battleships
 */
public interface Ship {
  /**
   * Gets the name of the ship
   *
   * @return the name of the ship
   */
  String getName();

  /**
   * Gets the original size of the ship
   *
   * @return the original size of the ship
   */
  int getOriginalSize();

  /**
   * Adds a coord that the ship is currently in.
   *
   * @param coord the coord that the ship is currently in.
   */
  void addCoord(Coord coord);

  /**
   * Gets the current size of the ship
   *
   * @return the current size of the ship,
   *          same as original size if the ship has not been hit.
   */
  int getCurrentSize();

  /**
   * Gets the first letter of the ship name
   *
   * @return first letter of the ship name
   */
  String getFirstLetter();

  /**
   * Ship was hit
   */
  void shipHit();

  /**
   * Check if ship has been hit yet.
   *
   * @return true if ship was hit, false otherwise
   */
  boolean checkShipHit();

  /**
   * Checks if the ship is still in water.
   *
   * @return true if ship is still in water, false otherwise.
   */
  boolean shipInWater();

  /**
   * Get ship type
   *
   * @return the ship type
   */
  ShipType getShipType();

  /**
   * Get ship positions
   *
   * @return the list of the ships coords
   */
  List<Coord> getPositions();
}