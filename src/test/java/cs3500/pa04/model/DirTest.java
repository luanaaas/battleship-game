package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing Direction (Dir) enum class and values
 */
class DirTest {
  private Dir horizontal;
  private Dir vertical;

  /**
   * Initializes direction enum parts
   */
  @BeforeEach
  void setUp() {
    horizontal = Dir.HORIZONTAL;
    vertical = Dir.VERTICAL;
  }

  /**
   * Tests enum values, if correct direction and value
   */
  @Test
  void testValues() {
    assertEquals("HORIZONTAL", horizontal.name());
    assertEquals("VERTICAL", vertical.name());

    assertEquals(0, horizontal.ordinal());
    assertEquals(1, vertical.ordinal());
  }


}