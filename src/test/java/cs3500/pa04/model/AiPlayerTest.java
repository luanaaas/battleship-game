package cs3500.pa04.model;

import static cs3500.pa04.model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Represents testing AIPlayer class and methods
 */
class AiPlayerTest {

  private AiPlayer aiPlayer;
  private GameBoard aiBoard;
  private Map<ShipType, Integer> specifications;
  private Ship ship;
  private Coord c1;
  private Coord c2;
  private Coord c3;
  private List<Coord> location;
  private Random rand = new Random(1);

  /**
   * Initializes classes used for the Ai player
   */
  @BeforeEach
  void setUp() {
    aiBoard = new GameBoard();
    aiPlayer = new AiPlayer(aiBoard, rand);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 2);
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
    assertEquals("AI", aiPlayer.name());
  }

  /**
   * Tests setting up the ships based on input
   */
  @Test
  void testSetup() {
    List<Ship> ships = aiPlayer.setup(9, 8, specifications);
    assertEquals(8, aiBoard.getHeight());
    assertEquals(9, aiBoard.getWidth());
    assertEquals(5, ships.size());
  }

  /**
   * Tests taking user shots
   */
  @Test
  void testTakeShots() {
    aiPlayer.setup(9, 9, specifications);
    List<Coord> shots = aiPlayer.takeShots();
    assertEquals(aiBoard.shipsLeft(), shots.size());
    for (Coord shot : shots) {
      shot.updateToHit();
      assertTrue(shot.getX() >= 0 && shot.getX() < aiBoard.getHeight());
      assertTrue(shot.getY() >= 0 && shot.getY() < aiBoard.getWidth());
    }

    this.aiBoard = new GameBoard();
    this.aiPlayer = new AiPlayer(this.aiBoard, this.rand);
    this.aiPlayer.setup(15, 15, this.specifications);

    for (Coord[] c : this.aiBoard.getBoardCoord()) {
      for (Coord coord : c) {
        coord.updateToHit();
      }
    }
    List<Coord> emptyShots = aiPlayer.takeShots();
    assertEquals(0, emptyShots.size());
  }


  /**
   * Tests the end game
   */
  @Test
  void testEndGame() {
    aiPlayer.endGame(GameResult.DRAW, "");
    aiPlayer.endGame(GameResult.WIN, "");
  }


  /**
   * Tests if Ai player reports the correct damage
   */
  @Test
  void testReportDamage() {
    aiPlayer.setup(15, 15, specifications);
    List<Coord> damage = new ArrayList<>();

    damage.add(new Coord(9, 4));
    damage.add(new Coord(9, 5));
    damage.add(new Coord(9, 6));
    damage.add(new Coord(10, 3));
    damage.add(new Coord(0, 0));

    List<Coord> reportDamageCheck = aiPlayer.reportDamage(damage);

    damage.add(new Coord(4, 9));
    damage.add(new Coord(5, 9));
    assertEquals(0, reportDamageCheck.size());
  }
}