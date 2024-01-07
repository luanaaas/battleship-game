package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents testing the game view class and methods
 */
class GameViewTest {

  private GameView view;
  private OutputStream output;
  private InputStream input;

  private Coord[][] testBoard() {
    Coord[][] board = new Coord[3][3];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = new Coord(i, j);
      }
    }
    return board;
  }

  /**
   * Tests prompting input
   */
  @Test
  void testPrompts() {
    String inputString = "6 6\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    String prompt = "Please Enter: ";
    try {
      view.prompts(prompt);
      assertEquals(prompt + "\n", output.toString());
    } catch (IOException e) {
      fail("IOException should not be thrown");
    }
  }

  /**
   * Tests prompting grid size
   */
  @Test
  void testPromptGridSize() {
    String inputString = "6 6\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    int[] gridSize = view.promptGridSize();
    assertEquals(6, gridSize[0]);
    assertEquals(6, gridSize[1]);
  }

  /**
   * Tests prompting fleet size
   */
  @Test
  void testPromptFleetSize() {
    String inputString = "1 1 1 2\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    int[] fleetSize = view.promptFleetSize();
    assertEquals(1, fleetSize[0]);
    assertEquals(1, fleetSize[1]);
    assertEquals(1, fleetSize[2]);
    assertEquals(2, fleetSize[3]);
  }

  /**
   * Tests displaying the game boards
   */
  @Test
  void testDisplayBoards() {
    String inputString = "3 3\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));

    Coord[][] aiBoard = testBoard();
    Coord[][] userBoard = testBoard();
    try {
      view.displayBoards(aiBoard, userBoard);
      assertEquals("\nOpponent Board Data: \n"
          + "0 0 0 \n"
          + "0 0 0 \n"
          + "0 0 0 \n"
          + "\nYour Board: \n"
          + "0 0 0 \n"
          + "0 0 0 \n"
          + "0 0 0 \n", output.toString());
    } catch (IOException e) {
      fail("IOException should not be thrown");
    }
  }

  /**
   * Tests prompting user shots
   */
  @Test
  void testPromptShots() {
    String inputString = "1 2\n3 4\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    try {
      List<Coord> shots = view.promptShots(2);
      assertEquals(2, shots.size());
      assertEquals(1, shots.get(0).getX());
      assertEquals(2, shots.get(0).getY());
      assertEquals(3, shots.get(1).getX());
      assertEquals(4, shots.get(1).getY());
    } catch (IOException e) {
      fail("IOException should not be thrown");
    }
  }

  /**
   * Tess showing ending statistics
   */
  @Test
  void testShowEndStats() {
    String inputString = "6 6\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    try {
      view.showEndStats(GameResult.WIN, "you sunk all your opponents ships!");
      assertEquals("\nYou won! The reason was you sunk all your opponents ships!",
          output.toString());

      view.showEndStats(GameResult.LOSE, "all your ships we're sunk.");
      assertTrue(output.toString().contains("You lost. The reason was all your ships we're sunk."));

      view.showEndStats(GameResult.DRAW, "both you and your opponent sunk each"
          + "other's ships at the same time.");
      assertTrue(output.toString().contains("\nThere was a draw! "
          + "The reason was both you and your opponent sunk each"
          + "other's ships at the same time."));
    } catch (IOException e) {
      fail("IOException should not be thrown");
    }
  }
}