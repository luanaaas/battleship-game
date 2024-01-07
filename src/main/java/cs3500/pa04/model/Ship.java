package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a ship in the game
 */
public class Ship {

  private ShipType type;
  private List<Coord> locations; // locations occupied by ship
  private boolean sunk;
  private Dir dir;

  @JsonCreator
  Ship(
      @JsonProperty("coord") Coord start,
      @JsonProperty("length") int length,
      @JsonProperty("direction") String dir) {
    start = this.getStart();
    length = this.getLength();
    dir = this.getDir().toString();
  }

  Ship(ShipType type, List<Coord> locations) {
    this.type = type;
    this.locations = locations;
    this.sunk = false; // sunk initialized as false
  }

  Ship(ShipType type, List<Coord> locations, Dir dir) {
    this.type = type;
    this.locations = locations;
    this.sunk = false; // sunk initialized as false
    this.dir = dir;
  }



  /**
   * Getter method for the locations of the ship
   *
   * @return the list of coordinates the ship covers
   */
  public List<Coord> getLocation() {
    return locations;
  }

  /**
   * Determines if this ship's locations have given coordinates
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return if coordinates are included in the ship's locations
   */
  public boolean hasCoord(int x, int y) {
    for (Coord coord : locations) {
      if (coord.getX() == x && coord.getY() == y) { // coords part of ship
        return true;
      }
    }
    return false;
  }

  /**
   * Gets this ships sunk flag
   *
   * @return if this ship is sunk
   */
  public boolean isSunk() {
    return sunk;
  }

  /**
   * Updates this ship to be sunk if all it's locations are hit
   */
  public void updateSunk() {
    for (Coord c : locations) {
      if (!c.getIsHit()) {
        sunk = false; // if any coordinate not hit, it's not sunk
        break;
      } else {
        sunk = true;
      }
    }
  }

  /**
   * Gets this ships starting coordinate
   *
   * @return ships starting coordinate
   */
  public Coord getStart() {
    return locations.get(0);
  }

  /**
   * Gets the length of the ship
   *
   * @return length of the ship
   */
  public int getLength() {
    return type.getSize();
  }

  /**
   * Gets the direction of the ship
   *
   * @return the direction of the dip
   */
  public Dir getDir() {
    return dir;
  }

  /**
   * Determines if this ships coordinates overlap with the given ships coordinates
   *
   * @param coordList the list of coordinates the given ship covers
   * @return if ships is has overlapping coordinates
   */
  public boolean checkIfOverlapWithCoord(List<Coord> coordList) {
    boolean b = false;
    for (Coord c : this.locations) {
      for (Coord givenCoord : coordList) {
        if (c.equals(givenCoord)) {
          return true;
        }
      }
    }
    return b;
  }

  /**
   * Updates ship direction to be horizontal
   */
  public void updateHorizontal() {
    this.dir = Dir.HORIZONTAL;
  }

  /**
   * Updates ship direction to be vertical
   */
  public void updateVertical() {
    this.dir = Dir.VERTICAL;
  }
}
