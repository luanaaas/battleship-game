package cs3500.pa04.model;

import static cs3500.pa04.model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.view.GameView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing UserPlayer class and methods
 */
class UserPlayerTest {
  private GameView view;
  private GameBoard board;
  private OutputStream output;
  private InputStream input;
  private UserPlayer userPlayer;
  private Map<ShipType, Integer> specifications;
  private ManualDataModel mdm;
  private Ship ship;
  private Coord c1;
  private Coord c2;
  private Coord c3;
  private List<Coord> location;
  private Random rand = new Random(1);

  /**
   * Initializes classes used for a user player
   */
  @BeforeEach
  void setUp() {
    String inputString = "7 7\n1 1 1 1\n1 1\n0 1\n4 5\n3 3\n";
    output = new ByteArrayOutputStream();
    input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);
    Readable sys = new InputStreamReader(System.in);
    view = new GameView(sys, new PrintStream(output));
    board = new GameBoard();
    mdm = new ManualDataModel(view);
    userPlayer = new UserPlayer(board, rand, mdm);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(SUBMARINE, 1);
    location = new ArrayList<>();
    c1 = new Coord(1, 1);
    c2 = new Coord(2, 1);
    c3 = new Coord(3, 1);
    location.add(c1);
    location.add(c2);
    location.add(c3);
    ship = new Ship(SUBMARINE, location);
  }

  /**
   * Tests name returned
   */
  @Test
  void testName() {
    assertEquals("User", userPlayer.name());
  }

  /**
   * Tests taking user shots
   */
  @Test
  void testTakeShots() {
    userPlayer.setup(7, 7, specifications);
    List<Coord> shots = userPlayer.takeShots();
    assertEquals(4, board.shipsLeft());
    for (Coord c : shots) {
      for (Ship s : board.getShipsOnBoard()) {
        if (s.getLocation().contains(c)) {
          c.updateToHit();
        }
      }
    }
    assertEquals(2, userPlayer.takeShots().size());
  }

  /**
   * Tests the end game
   */
  @Test
  void testEndGame() {
    userPlayer.endGame(GameResult.DRAW, "");
    userPlayer.endGame(GameResult.WIN, "");
  }
}