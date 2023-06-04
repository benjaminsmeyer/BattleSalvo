package cs3500.pa03.model.coords;

import cs3500.pa03.model.ships.Ship;

/**
 * Interface for Coords
 */
public interface Coord {
  /**
   * Gets the X coordinate of the board
   *
   * @return the x coordinate
   */
  int getAxisX();

  /**
   * Gets the Y coordinate of the board
   *
   * @return the y coordinate
   */
  int getAxisY();

  /**
   * Checks if the Coord has been hit
   *
   * @return true if spot has been hit, false otherwise.
   */
  boolean checkHit();

  /**
   * Spot has been hit
   */
  void spotHit();

  /**
   * Checks if the spot is in use
   *
   * @return true if spot is used, false otherwise.
   */
  boolean checkSpotInUse();

  /**
   * Returns the ship that took the spot
   *
   * @return the ship that took the spot
   * @throws IllegalArgumentException when ship did not take the spot
   */
  Ship spotTaken();
}

