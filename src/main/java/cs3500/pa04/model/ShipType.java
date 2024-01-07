package cs3500.pa04.model;

/**
 * Represents one of four ship types
 */
public enum ShipType {
  /**
   * Represents a carrier ship of size 6
   */
  CARRIER(6),
  /**
   * Represents a battleship of size 5
   */
  BATTLESHIP(5),
  /**
   * Represents a destroyer ship of size 4
   */
  DESTROYER(4),
  /**
   * Represents a submarine ship of size 3
   */
  SUBMARINE(3);

  private final int size;

  ShipType(int size) {
    this.size = size;
  }

  /**
   * Getter method for the size of the ship
   *
   * @return the respective size of the ship
   */
  public int getSize() {
    return size;
  }
}
