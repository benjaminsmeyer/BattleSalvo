package cs3500.pa03.model.coords;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ships.Ship;
import java.util.NoSuchElementException;

/**
 * Coordinates on the board
 */
public class Coord {
  @JsonProperty("x")
  private final int axisX;
  @JsonProperty("y")
  private final int axisY;
  private final boolean spotInUse;
  private final Ship ship;
  private boolean spotHit;

  /**
   * Setups the coords
   *
   * @param axisX the x coordinate
   * @param axisY the y coordinate
   */
  public Coord(@JsonProperty("x") int axisX,
               @JsonProperty("y") int axisY) {
    this.axisX = axisX;
    this.axisY = axisY;
    this.spotInUse = false;
    this.ship = null;
    this.spotHit = false;
  }

  /**
   * Setups the coords with ship taking the spot
   *
   * @param axisX the x coordinate
   * @param axisY the y coordinate
   * @param ship the ship
   */
  public Coord(int axisX, int axisY, Ship ship) {
    this.axisX = axisX;
    this.axisY = axisY;
    this.spotInUse = true;
    this.ship = ship;
    this.spotHit = false;
  }

  /**
   * Gets the X coordinate of the board
   *
   * @return the x coordinate
   */
  public int getAxisX() {
    return axisX;
  }

  /**
   * Gets the Y coordinate of the board
   *
   * @return the y coordinate
   */
  public int getAxisY() {
    return axisY;
  }

  /**
   * Checks if the Coord has been hit
   *
   * @return true if spot has been hit, false otherwise.
   */
  public boolean checkHit() {
    return spotHit;
  }

  /**
   * Spot has been hit
   */
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
  public boolean checkSpotInUse() {
    return spotInUse;
  }

  /**
   * Returns the ship that took the spot
   *
   * @return the ship that took the spot
   * @throws NoSuchElementException when ship did not take the spot
   */
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
  public String toString() {
    return String.format("(%d, %d)", axisX, axisY);
  }
}
