package cs3500.pa03.model.ships;

/**
 * An enum containing the basic details for each ship
 */
public enum ShipType {
  Carrier(6),
  Battleship(5),
  Destroyer(4),
  Submarine(3),
  Empty(0);

  private final int size;

  /**
   * Setups the ship types
   *
   * @param size the size of the ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the name of the ship
   *
   * @return the name of the ship
   */
  public String getName() {
    return toString();
  }

  /**
   * Gets the size of the ship
   *
   * @return the size of the ship
   */
  public int getSize() {
    return size;
  }

  /**
   * Gets the first letter of the ship name
   *
   * @return first letter of the ship name
   */
  public String getFirstLetter() {
    return getName().substring(0, 1);
  }
}

