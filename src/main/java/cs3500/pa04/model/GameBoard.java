package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board of the game
 */
public class GameBoard {
  private int height;
  private int width;
  private Coord[][] board;

  /**
   * Represents GameBoard constructor
   */
  public GameBoard() {
    this.height = 0;
    this.width = 0;
    this.board = null;
  }
  /**
   * Initializes this board
   *
   * @param h height of the board
   * @param w width of the board
   */
  public void initializeBoard(int h, int w) {
    this.height = w; // height and width SWAPPED
    this.width = h;
    this.board = new Coord[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        board[row][col] = new Coord(row, col);
      }
    }
  }

  /**
   * Places given ships on this board
   *
   * @param ships the ships to be places on the board
   */
  public void placeShip(List<Ship> ships) {
    for (Ship ship : ships) {
      List<Coord> shipCoordinates = ship.getLocation();
      for (Coord coordinate : shipCoordinates) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        coordinate.setShip(ship);
        board[x][y] = coordinate; // place ship on board
      }
    }
  }

  /**
   * Getter method for the board's coordinates
   *
   * @return this board
   */
  public Coord[][] getBoardCoord() {
    return board;
  }

  /**
   * To obtain the ships on the board
   *
   * @return ships on this board
   */
  public List<Ship> getShipsOnBoard() {
    List<Ship> ships = new ArrayList<>();
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        Coord coord = board[x][y];
        if (coord != null && coord.hasShip() && !ships.contains(coord.getShip())) {
          ships.add(coord.getShip());
        }
      }
    }
    return ships;
  }

  /**
   * Updates the coordinates on this board
   *
   * @param shots the shots to update this board's hits
   */
  public void updateBoard(List<Coord> shots) {
    for (Coord shot : shots) {
      int x = shot.getX();
      int y = shot.getY();
      board[x][y].updateToHit();
    }
  }

  /**
   * Determines how many ships aren't sunk yet
   *
   * @return the number of ships left on this board
   */
  public int shipsLeft() {
    int left = 0;
    List<Ship> shipsOnBoard = getShipsOnBoard();
    for (Ship s : shipsOnBoard) {
      s.updateSunk();
      if (!s.isSunk()) {
        left++;
      }
    }
    return left;
  }

  /**
   * Determines if all ships are sunk, i.e. the game is over
   *
   * @return if the game is over
   */
  public boolean isGameOver() {
    return shipsLeft() == 0;
  }

  /**
   * Getter method for the height of this board
   *
   * @return this board's height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Getter method for the width of this board
   *
   * @return this board's width
   */
  public int getWidth() {
    return width;
  }


}
