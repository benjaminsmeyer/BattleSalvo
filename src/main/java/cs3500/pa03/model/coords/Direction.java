package cs3500.pa03.model.coords;

/**
 * The direction to place the ships in
 */
public enum Direction {
  UP(0),
  DOWN(1),
  RIGHT(2),
  LEFT(3);

  private final int direction;

  /**
   * Setups the direction for the ships
   *
   * @param direction the ship direction
   */
  Direction(int direction) {
    this.direction = direction;
  }

  /**
   * Gets the direction for the ship
   *
   * @return the direction for the ship
   */
  public int getDirection() {
    return direction;
  }
}
