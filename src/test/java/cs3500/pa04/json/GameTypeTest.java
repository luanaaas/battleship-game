package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Dir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing one of two game types (single or multi)
 */
class GameTypeTest {

  private GameType single;
  private GameType multi;

  /**
   * Initializes game type enum parts
   */
  @BeforeEach
  void setUp() {
    single = GameType.SINGLE;
    multi = GameType.MULTI;
  }

  /**
   * Tests enum values, if correct type of game
   */
  @Test
  void testValues() {
    assertEquals("SINGLE", single.name());
    assertEquals("MULTI", multi.name());

    assertEquals(0, single.ordinal());
    assertEquals(1, multi.ordinal());
  }
}