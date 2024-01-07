package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Represents a coordinate on the board
 */
public class Coord {

  private final int xxRow;
  private final int yyCol;
  private boolean isHit;
  private Ship ship;

  /**
   * Represents coordinate constructor
   *
   * @param x the x or row location
   * @param y the y or column location
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.xxRow = x;
    this.yyCol = y;
    this.isHit = false;
    this.ship = null;
  }

  /**
   * Getter method for the x placement of a coordinate
   *
   * @return the x coordinate
   */
  public int getX() {
    return xxRow;
  }

  /**
   * Getter method for the y placement of a coordinate
   *
   * @return the y coordinate
   */
  public int getY() {
    return yyCol;
  }

  /**
   * Getter method for if coordinate is hit
   *
   * @return if coordinate is hit
   */
  public boolean getIsHit() {
    return isHit;
  }

  /**
   * Updates this coordinate to be hit
   */
  public void updateToHit() {
    isHit = true;
  }

  /**
   * Getter method for ship on the coordinate
   *
   * @return ship on this coordinate
   */
  public Ship getShip() {
    return ship;
  }

  /**
   * Updates this coordinate have given ship on it
   *
   * @param ship ship to be on coordinate
   */
  public void setShip(Ship ship) {
    this.ship = ship;
  }

  /**
   * Determines if there's a ship here or if it's null
   *
   * @return if this coordinate has a ship
   */
  public boolean hasShip() {
    return ship != null;
  }

  /**
   * The way the coordinate should look on the opponents board
   *
   * @return the respective look of the coordinate
   */
  public char getOpponentLook() {
    if (hasShip() && isHit) {
      return 'H';  // Ship hit
    } else if (!hasShip() && isHit) {
      return 'M';  // Missed
    } else {
      return '0';  // Empty
    }
  }

  /**
   * The way the coordinate should look on the users board
   *
   * @return the respective look of the coordinate
   */
  public char getUserLook() {
    if (hasShip() && isHit) {
      return 'H';  // Ship hit
    } else if (hasShip()) {
      return 'S';  // Ship there but not hit
    } else if (!hasShip() && isHit) {
      return 'M';  // Missed shot
    } else {
      return '0';  // Empty
    }
  }

  /**
   * Overrides equals for comparison
   *
   * @param obj given coordinate object to compare
   * @return if the coordinates equal each other
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord other = (Coord) obj;
    return xxRow == other.xxRow && yyCol == other.yyCol;
  }

  /**
   * Overrides hashCode
   *
   * @return hash code of the coordinate
   */
  @Override
  public int hashCode() {
    return Objects.hash(xxRow, yyCol);
  }
}
