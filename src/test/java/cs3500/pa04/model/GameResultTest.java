package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing GameResult enum class and values
 */
class GameResultTest {

  private GameResult win;
  private GameResult lose;
  private  GameResult draw;

  /**
   * Initializes game result enum parts
   */
  @BeforeEach
  void setUp() {
    win = GameResult.WIN;
    lose = GameResult.LOSE;
    draw = GameResult.DRAW;
  }

  /**
   * Tests enum values, if correct game result and value
   */
  @Test
  void testValues() {
    assertEquals("WIN", win.name());
    assertEquals("LOSE", lose.name());
    assertEquals("DRAW", draw.name());

    assertEquals(0, win.ordinal());
    assertEquals(1, lose.ordinal());
    assertEquals(2, draw.ordinal());
  }
}