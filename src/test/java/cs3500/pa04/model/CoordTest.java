package cs3500.pa04.model;

import static cs3500.pa04.model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Represents testing Coord test class and methods
 */
class CoordTest {
  private Coord coord11;
  private Coord coord00;
  private Coord coord01;
  private Coord coord02;
  private Ship ship;
  private List<Coord> locations;

  /**
   * Initializes different coordinates with x and y
   */
  @BeforeEach
  void setUp() {
    int x0 = 0;
    int y0 = 0;
    int x1 = 1;
    int y1 = 1;
    coord11 = new Coord(x1, y1);
    coord00 = new Coord(x0, y0);
    coord01 = new Coord(x0, y1);
    int y2 = 2;
    coord02 = new Coord(x0, y2);
    locations = new ArrayList<>();
    locations.add(coord00);
    locations.add(coord01);
    locations.add(coord02);
    ship = new Ship(SUBMARINE, locations);
  }

  /**
   * Tests getter method of coordinate x value
   */
  @Test
  void testGetX() {
    assertEquals(1, coord11.getX());
    assertEquals(0, coord00.getX());
    assertEquals(0, coord01.getX());
  }

  /**
   * Tests getter method of coordinate y value
   */
  @Test
  void testGetY() {
    assertEquals(1, coord11.getY());
    assertEquals(0, coord00.getY());
    assertEquals(1, coord01.getY());
  }

  /**
   * Tests getter method of if coordinate is hit
   */
  @Test
  void testGetIsHit() {
    assertFalse(coord00.getIsHit());
    coord00.updateToHit();
    assertTrue(coord00.getIsHit());
  }

  /**
   * Tests updating the coordinate to be hit
   */
  @Test
  void testUpdateToHit() {
    assertFalse(coord11.getIsHit());
    coord11.updateToHit();
    assertTrue(coord11.getIsHit());
  }

  /**
   * Tests getting the ship on the coordinate
   */
  @Test
  void testGetShip() {
    assertNull(coord00.getShip());
    coord00.setShip(ship);
    assertNotNull(coord00.getShip());
  }

  /**
   * Tests setting a ship on the coordinate
   */
  @Test
  void testSetShip() {
    assertNull(coord01.getShip());
    coord01.setShip(ship);
    assertNotNull(coord01.getShip());
  }

  /**
   * Tests if coordinate has a ship
   */
  @Test
  void testHasShip() {
    assertFalse(coord02.hasShip());
    coord02.setShip(ship);
    assertTrue(coord02.hasShip());
    assertFalse(coord11.hasShip());
  }

  /**
   * Tests getting the right opponent character look of the coordinate
   */
  @Test
  void testGetOpponentLook() {
    assertEquals('0', coord00.getOpponentLook());

    coord00.setShip(ship);
    assertEquals('0', coord00.getOpponentLook());

    coord00.updateToHit();
    assertEquals('H', coord00.getOpponentLook());

    coord00.setShip(null);
    assertEquals('M', coord00.getOpponentLook());
  }

  /**
   * Tests getting the right user character look of the coordinate
   */
  @Test
  void testGetUserLook() {
    assertEquals('0', coord00.getUserLook());

    coord00.setShip(ship);
    assertEquals('S', coord00.getUserLook());

    coord00.updateToHit();
    assertEquals('H', coord00.getUserLook());

    coord00.setShip(null);
    assertEquals('M', coord00.getUserLook());
  }

  /**
   * Tests overriding equals of coordinates
   */
  @Test
  void testEquals() {
    Coord anotherCoord00 = new Coord(0, 0);
    assertEquals(anotherCoord00, coord00);
    assertNotEquals(anotherCoord00, coord01);
    assertNotEquals(anotherCoord00, null);
    assertNotEquals(anotherCoord00, "0, 0");
    Coord coord00Again = new Coord(0, 0);
    assertEquals(coord00Again, anotherCoord00);

  }

  /**
   * Tests overriding hashcode of coordinates
   */
  @Test
  void testHashCode() {
    Coord anotherCoord00 = new Coord(0, 0);
    assertEquals(anotherCoord00.hashCode(), coord00.hashCode());
    assertNotEquals(anotherCoord00.hashCode(), coord11.hashCode());
  }
}