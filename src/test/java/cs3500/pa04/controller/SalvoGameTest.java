package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.GameBoard;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.view.GameView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing SalvoGame controller class and methods
 */
class SalvoGameTest {

  private SalvoGame game;
  private GameView view;
  private OutputStream output;
  private InputStream input;
  private Player ai;
  private GameBoard board;
  private Random rand = new Random(1);

  /**
   * Initializes class, input, and random for the salvo game
   */
  @BeforeEach
  void setUp() {
    String inputString = "6 6\n1 1 1 1\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    this.board = new GameBoard();
    this.ai = new AiPlayer(board, rand);
  }


  /**
   * Tests running the game
   */
  @Test
  void testRunGame() {
    String inputString = "2 18\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedGridPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game\n"
        + "must be in the range (6, 15), inclusive. Try again!";

    assertTrue(output.toString().contains(expectedGridPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Tests board prompting and invalid response
   */
  @Test
  void testInvalidBoardPrompting() {
    String inputString = "5 5\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game\n"
        + "must be in the range (6, 15), inclusive. Try again!";

    assertTrue(output.toString().contains(expectedPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }

  /**
   * Tests board prompting and invalid response
   */
  @Test
  void testInvalidHeightBoardPrompting() {
    String inputString = "5 10\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game\n"
        + "must be in the range (6, 15), inclusive. Try again!";

    assertTrue(output.toString().contains(expectedPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Tests board prompting and invalid response
   */
  @Test
  void testInvalidWidthBoardPrompting() {
    String inputString = "20 10\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game\n"
        + "must be in the range (6, 15), inclusive. Try again!";

    assertTrue(output.toString().contains(expectedPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Tests board prompting and invalid response
   */
  @Test
  void testInvalidWidthDimesionBoardPrompting() {
    String inputString = "10 20\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game\n"
        + "must be in the range (6, 15), inclusive. Try again!";

    assertTrue(output.toString().contains(expectedPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Tests board prompting and invalid response
   */
  @Test
  void testValidBoardDimensions() {
    String inputString = "6 6\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedPrompting = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ";
    String expectedOutput = "Please enter your fleet in the order"
        + "[Carrier, Battleship, Destroyer, Submarine]\n"
        + "Remember, your fleet may not exceed size " + 6 + "." + System.lineSeparator();

    assertTrue(output.toString().contains(expectedPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }


  /**
   * Tests fleet prompting and invalid response
   */
  @Test
  void testInvalidFleet() {
    String inputString = "7 7\n4 3 2 2\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedFleetPrompting = "Please enter your fleet in the order"
        + "[Carrier, Battleship, Destroyer, Submarine]\n"
        + "Remember, your fleet may not exceed size " + 7 + ".";
    String expectedOutput = "Uh Oh! You've entered invalid fleet sizes. \n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]. \n"
        + "Remember, your fleet may not exceed size " + 7 + ".\n";

    assertTrue(output.toString().contains(expectedFleetPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }

  /**
   * Tests out of bounds shot input
   */
  @Test
  void testInvalidShots() {
    String inputString = "6 6\n1 0 0 0\n10 11\n5 7\n6 1\n2 2\n";
    input = new ByteArrayInputStream(inputString.getBytes());
    output = new ByteArrayOutputStream();
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    game = new SalvoGame(sys, new PrintStream(output), ai, board);
    game.run();

    String expectedShotPrompting = "-------------------------------------------\n";
    String expectedOutput = "Shots out of board bounds, try again!";

    assertTrue(output.toString().contains(expectedShotPrompting));
    assertTrue(output.toString().contains(expectedOutput));
  }
}