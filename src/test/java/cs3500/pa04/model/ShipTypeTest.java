package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing ShipType enum class values and methods
 */
class ShipTypeTest {

  private ShipType carrier;
  private ShipType battleship;
  private ShipType destroyer;
  private ShipType submarine;

  /**
   * Initializes ship type enum parts
   */
  @BeforeEach
  void setUp() {
    carrier = ShipType.CARRIER;
    battleship = ShipType.BATTLESHIP;
    destroyer = ShipType.DESTROYER;
    submarine = ShipType.SUBMARINE;
  }

  /**
   * Tests if right size assigned to shipType
   */
  @Test
  void testGetSize() {
    assertEquals(6, carrier.getSize());
    assertEquals(5, battleship.getSize());
    assertEquals(4, destroyer.getSize());
    assertEquals(3, submarine.getSize());
  }

  /**
   * Tests enum values, if correct ship type and value
   */
  @Test
  void testValues() {
    assertEquals("CARRIER", carrier.name());
    assertEquals("BATTLESHIP", battleship.name());
    assertEquals("DESTROYER", destroyer.name());
    assertEquals("SUBMARINE", submarine.name());

    assertEquals(0, carrier.ordinal());
    assertEquals(1, battleship.ordinal());
    assertEquals(2, destroyer.ordinal());
    assertEquals(3, submarine.ordinal());
  }
}