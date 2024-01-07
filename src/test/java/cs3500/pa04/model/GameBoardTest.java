package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Represents testing the game board class
 */
class GameBoardTest {
  GameBoard board;
  List<Ship> shipList;
  List<Coord> locations;
  Ship ship;

  /**
   * Initializes classes used for a game board
   */
  @BeforeEach
  void setUp() {
    board = new GameBoard();
    locations = new ArrayList<>();
    Coord c0 = new Coord(0, 0);
    Coord c1 = new Coord(0, 1);
    Coord c2 = new Coord(0, 2);
    Coord c3 = new Coord(0, 3);
    Coord c4 = new Coord(0, 4);
    Coord c5 = new Coord(0, 5);
    locations.add(c0);
    locations.add(c1);
    locations.add(c2);
    locations.add(c3);
    locations.add(c4);
    locations.add(c5);
    ship = new Ship(ShipType.CARRIER, locations);
    shipList = new ArrayList<>();
    shipList.add(ship);
  }

  @Test
  public void testInitializeBoard() {
    int height = 10;
    int width = 10;
    board.initializeBoard(height, width);

    assertEquals(height, board.getHeight());
    assertEquals(width, board.getWidth());

    Coord[][] gameBoard = board.getBoardCoord();
    assertNotNull(board);
    assertEquals(height, gameBoard.length);
    assertEquals(width, gameBoard[0].length);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Coord coord = gameBoard[row][col];
        assertNotNull(coord);
        assertEquals(row, coord.getX());
        assertEquals(col, coord.getY());
      }
    }
  }



  /**
   * Tests placing the ships on the board
   */
  @Test
  void testPlaceShip() {
    board.initializeBoard(10, 10);
    board.placeShip(shipList);
    assertTrue(board.getBoardCoord()[0][0].hasShip());
    assertTrue(board.getBoardCoord()[0][1].hasShip());
    assertTrue(board.getBoardCoord()[0][2].hasShip());
  }

  /**
   * Tests getting the 2D array board coordinates
   */
  @Test
  void testGetBoardCoord() {
    board.initializeBoard(6, 6);
    Coord[][] boardCoord = board.getBoardCoord();
    assertNotNull(boardCoord);
    assertEquals(6, boardCoord.length);
    assertEquals(6, boardCoord[0].length);
  }

  /**
   * Tests getting the ships on the board
   */
  @Test
  void testGetShipsOnBoard() {
    board.initializeBoard(10, 10);
    board.placeShip(shipList);
    assertEquals(1, board.getShipsOnBoard().size());
  }

  /**
   * Tests updating the board coordinates
   */
  @Test
  void testUpdateBoard() {
    board.initializeBoard(6, 6);
    Coord[][] boardBeforeShot = board.getBoardCoord();
    assertFalse(boardBeforeShot[2][2].getIsHit());
    assertFalse(boardBeforeShot[2][3].getIsHit());
    assertFalse(boardBeforeShot[4][4].getIsHit());
    assertFalse(boardBeforeShot[4][5].getIsHit());

    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(2, 2));
    shots.add(new Coord(4, 4));
    board.updateBoard(shots);

    Coord[][] boardAfterShot = board.getBoardCoord();
    assertTrue(boardAfterShot[2][2].getIsHit());
    assertFalse(boardAfterShot[2][3].getIsHit());
    assertTrue(boardAfterShot[4][4].getIsHit());
    assertFalse(boardAfterShot[4][5].getIsHit());
  }

  /**
   * Tests the amount of ships left on the board
   */
  @Test
  void testShipsLeft() {
    board.initializeBoard(6, 6);
    board.placeShip(shipList);
    Coord[][] boardCoord = board.getBoardCoord();
    assertEquals(1, board.shipsLeft());
    boardCoord[0][0].updateToHit();
    boardCoord[0][1].updateToHit();
    boardCoord[0][2].updateToHit();
    boardCoord[0][3].updateToHit();
    boardCoord[0][4].updateToHit();
    boardCoord[0][5].updateToHit();
    assertEquals(0, board.shipsLeft());
  }

  /**
   * Tests if the game is over if ships left are 0
   */
  @Test
  void testIsGameOver() {
    board.initializeBoard(6, 6);
    board.placeShip(shipList);
    Coord[][] boardCoord = board.getBoardCoord();
    assertFalse(board.isGameOver());
    boardCoord[0][0].updateToHit();
    boardCoord[0][1].updateToHit();
    boardCoord[0][2].updateToHit();
    boardCoord[0][3].updateToHit();
    boardCoord[0][4].updateToHit();
    boardCoord[0][5].updateToHit();
    assertTrue(board.isGameOver());
  }

  /**
   * Tests getting the height of the board
   */
  @Test
  void testGetHeight() {
    board.initializeBoard(7, 6);
    assertEquals(6, board.getHeight());
  }

  /**
   * Tests getting the width of the board
   */
  @Test
  void testGetWidth() {
    board.initializeBoard(7, 6);
    assertEquals(7, board.getWidth());
  }
}


