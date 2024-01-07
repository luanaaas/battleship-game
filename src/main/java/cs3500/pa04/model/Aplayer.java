package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an abstract player in game
 */
public abstract class Aplayer implements Player {

  /**
   * Represents the board of a player in the game
   */
  protected GameBoard board;
  /**
   * Represents the random used with a player's data
   */
  protected Random rand;

  /**
   * Represents an abstract player
   *
   * @param board this players board
   * @param rand  a random
   */
  public Aplayer(GameBoard board, Random rand) {
    this.board = board;
    this.rand = rand;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public abstract String name();

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> userShips = new ArrayList<>();
    board.initializeBoard(height, width);
    int min = Math.min(height, width);

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {

      ShipType shipType = entry.getKey();
      int shipCount = entry.getValue();

      for (int i = 0; i < shipCount; i++) {
        int shipSize = shipType.getSize();
        List<Coord> coords = new ArrayList<>();
        Ship ship = new Ship(shipType, coords);
        boolean validPlacement = false;
        while (!validPlacement) { // find valid ship placement
          boolean isHorizontal = rand.nextBoolean();
          if (isHorizontal) { // min -> width & height
            int startX = rand.nextInt(width - shipSize + 1);
            int startY = rand.nextInt(height);
            validPlacement =
                canPlaceHorizontally(startX, startY, shipSize, width, userShips, coords);
            if (validPlacement) {
              ship.updateHorizontal();
            }
          } else {
            int startX = rand.nextInt(width);
            int startY = rand.nextInt(height - shipSize + 1);
            validPlacement =
                canPlaceVertically(startX, startY, shipSize, height, userShips, coords);
            if (validPlacement) {
              ship.updateVertical();
            }
          }
        }
        userShips.add(ship);
        board.placeShip(userShips);
      }
    }
    return userShips;
  }


  /**
   * Determine if horizontal ship placement is valid
   *
   * @param startX    the starting x position
   * @param startY    the starting y position
   * @param shipSize  current ships size
   * @param boardSize the size of the board
   * @param ships     the list of ships
   * @param coords    the list of coordinates
   * @return if ship can be places horizontally
   */
  private boolean canPlaceHorizontally(int startX, int startY, int shipSize,
                                       int boardSize, List<Ship> ships, List<Coord> coords) {
    if (startX + shipSize <= boardSize) {
      for (int x = startX; x < startX + shipSize; x++) {
        coords.add(new Coord(x, startY));
      }
    }
    return !isShipOverlap(ships, startX, startY, coords);
  }

  /**
   * Determine if vertical ship placement is valid
   *
   * @param startX    the starting x position
   * @param startY    the starting y position
   * @param shipSize  current ships size
   * @param boardSize the size of the board
   * @param ships     the list of ships
   * @param coords    the list of coordinates
   * @return if ship can be places vertically
   */
  private boolean canPlaceVertically(int startX, int startY, int shipSize,
                                     int boardSize, List<Ship> ships, List<Coord> coords) {
    if (startY + shipSize <= boardSize) {
      for (int y = startY; y < startY + shipSize; y++) {
        coords.add(new Coord(startX, y));
      }
    }
    return !isShipOverlap(ships, startX, startY, coords);
  }

  /**
   * Determine if ship overlaps with other ships placement
   *
   * @param ships current list of ships
   * @param x     coordinate x position
   * @param y     coordinate y position
   * @return if ship overlaps
   */
  private boolean isShipOverlap(List<Ship> ships, int x, int y, List<Coord> coordsList) {
    for (Ship ship : ships) {
      if (ship.hasCoord(x, y) || ship.checkIfOverlapWithCoord(coordsList)) {
        coordsList.clear();
        return true;
      }
    }
    return false;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return filtered list of given shots with all locations of shots that hit a ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damage = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      for (Ship s : board.getShipsOnBoard()) {
        if (s.getLocation().contains(shot)) {
          shot.updateToHit();
          s.updateSunk();
          damage.add(shot);
        }
      }
    }
    board.updateBoard(opponentShotsOnBoard);
    System.out.println();
    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      coord.updateToHit();
    }
  }


  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }
}
