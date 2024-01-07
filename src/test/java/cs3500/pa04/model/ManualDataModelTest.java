package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.view.GameView;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents the test class for the manually entered shots
 */
class  ManualDataModelTest {

  /**
   * Testing the shots the user enters during the game of BattleShip
   */
  @Test
  void testShots() {
    Readable readable = new StringReader("take-shots");
    Appendable appendable = new StringBuilder();
    GameView view = new GameView(readable, appendable);
    ManualDataModel test = new ManualDataModel(view);
    try {
      List<Coord> amountOfShotsToTake = test.shots(3);
      assertEquals(0, amountOfShotsToTake.size());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}