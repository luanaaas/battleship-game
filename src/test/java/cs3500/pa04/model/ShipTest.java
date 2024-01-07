package cs3500.pa04.model;

import static cs3500.pa04.model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing the ship class and methods
 */
class ShipTest {

  private Coord coord11;
  private Coord coord00;
  private Coord coord01;
  private Coord coord02;
  private Ship ship;
  private List<Coord> locations;
  private Dir dir;

  /**
   * Initializes ships with coordinates and types
   */
  @BeforeEach
  void setUp() {
    int x0 = 0;
    int y0 = 0;
    coord00 = new Coord(x0, y0);
    int x1 = 1;
    int y1 = 1;
    this.dir = Dir.HORIZONTAL;
    coord11 = new Coord(x1, y1);
    coord01 = new Coord(x0, y1);
    int y2 = 2;
    coord02 = new Coord(x0, y2);
    locations = new ArrayList<>();
    locations.add(coord00);
    locations.add(coord01);
    locations.add(coord02);
    ship = new Ship(SUBMARINE, locations, dir);
  }

  /**
   * Tests getting the location of the ship
   */
  @Test
  void testGetLocation() {
    List<Coord> shipLocations = ship.getLocation();
    assertEquals(3, shipLocations.size());
    assertEquals(locations, shipLocations);
  }

  /**
   * Tests if ship has certain coords
   */
  @Test
  void testHasCoord() {
    assertTrue(ship.hasCoord(0, 0));
    assertTrue(ship.hasCoord(0, 1));
    assertTrue(ship.hasCoord(0, 2));
    assertFalse(ship.hasCoord(1, 1));
  }

  /**
   * Tests if ship is sunk
   */
  @Test
  void testIsSunk() {
    assertFalse(ship.isSunk());
    coord00.updateToHit();
    coord01.updateToHit();
    coord02.updateToHit();
    ship.updateSunk();
    assertTrue(ship.isSunk());
  }

  /**
   * Tests if it gets the starting coordinate of the ship
   */
  @Test
  void testGetStart() {
    assertEquals(coord00, ship.getStart());
  }


  /**
   * Tests if getter gets the right direction of ship
   */
  @Test
  public void testDir() {
    assertEquals(Dir.HORIZONTAL, ship.getDir());
    ship.updateVertical();
    assertEquals(Dir.VERTICAL, ship.getDir());
  }

  /**
   * Tests if getting the right ship length
   */
  @Test
  void testGetLength() {
    assertEquals(3, ship.getLength());
  }

  /**
   * Tests if updating the ship direction to horizontal
   */
  @Test
  void testUpdateToHorizontal() {
    List<Coord> locations = new ArrayList<>();
    locations.add(new Coord(1, 1));
    locations.add(new Coord(1, 2));
    Ship ship1 = new Ship(ShipType.BATTLESHIP, locations, Dir.VERTICAL);

    ship1.updateHorizontal();
    assertEquals(Dir.HORIZONTAL, ship1.getDir());
  }

  /**
   * Tests if updating the ship direction to vertical
   */
  @Test
  void testUpdateToVertical() {
    List<Coord> locations = new ArrayList<>();
    locations.add(new Coord(1, 3));
    locations.add(new Coord(2, 3));
    Ship ship1 = new Ship(ShipType.BATTLESHIP, locations, Dir.HORIZONTAL);

    ship1.updateVertical();
    assertEquals(Dir.VERTICAL, ship1.getDir());
  }

  /**
   * Tests the other json creator constructor for ship
   */
  @Test
  void testJsonCreatorShip() {
    List<Coord> locations = new ArrayList<>();
    locations.add(new Coord(1, 3));
    locations.add(new Coord(2, 3));
    Ship ship1 = new Ship(ShipType.BATTLESHIP, locations, Dir.HORIZONTAL);

    int length = ship.getLength();
    assertEquals(Dir.HORIZONTAL, ship1.getDir());
    assertEquals(3, length);
  }


}