package cs3500.pa03.model.coords;

import cs3500.pa03.model.ships.Ship;
import java.util.NoSuchElementException;

/**
 * Coordinates on the board
 */
public class Coords implements Coord {
  private final int axisX;
  private final int axisY;
  private final boolean spotInUse;
  private final Ship ship;
  private boolean spotHit;
  private Direction direction;

  /**
   * Setups the coords
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * */
  public Coords(int x, int y) {
    this.axisX = x;
    this.axisY = y;
    this.spotInUse = false;
    this.ship = null;
    this.spotHit = false;
  }

  /**
   * Setups the coords with ship taking the spot
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @param ship the ship
   */
  public Coords(int x, int y, Ship ship) {
    this.axisX = x;
    this.axisY = y;
    this.spotInUse = true;
    this.ship = ship;
    this.spotHit = false;
  }

  /**
   * Gets the X coordinate of the board
   *
   * @return the x coordinate
   */
  @Override
  public int getAxisX() {
    return axisX;
  }

  /**
   * Gets the Y coordinate of the board
   *
   * @return the y coordinate
   */
  @Override
  public int getAxisY() {
    return axisY;
  }

  /**
   * Checks if the Coord has been hit
   *
   * @return true if spot has been hit, false otherwise.
   */
  @Override
  public boolean checkHit() {
    return spotHit;
  }

  /**
   * Spot has been hit
   */
  @Override
  public void spotHit() {
    spotHit = true;
    if (checkSpotInUse()) {
      spotTaken().shipHit();
    }
  }

  /**
   * Checks if the spot is in use by a ship.
   *
   * @return true if spot is used, false otherwise.
   */
  @Override
  public boolean checkSpotInUse() {
    return spotInUse;
  }

  /**
   * Returns the ship that took the spot
   *
   * @return the ship that took the spot
   * @throws NoSuchElementException when ship did not take the spot
   */
  @Override
  public Ship spotTaken() throws NoSuchElementException {
    if (ship == null) {
      throw new NoSuchElementException("No ship is in this spot.");
    }
    return ship;
  }

  /**
   * Returns the coordinates.
   *
   * @return a string of the coordinates
   */
  @Override
  public String toString() {
    return String.format("(%d, %d)", axisX, axisY);
  }
}
