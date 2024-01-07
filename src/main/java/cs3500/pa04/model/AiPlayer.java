package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an AI Player in game
 */
public class AiPlayer extends Aplayer {

  private List<Coord> prevShots;

  /**
   * Represents constructor for an AI player in the game
   *
   * @param board this AIs game board
   * @param rand random for this AI
   */
  public AiPlayer(GameBoard board, Random rand) {
    super(board, rand);
    this.prevShots = new ArrayList<>();
  }


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "AI";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int numShots = board.shipsLeft(); // shots equal to remaining ships
    int coordsLeft = (board.getHeight() * board.getWidth()) - prevShots.size();

    if (coordsLeft > numShots) { // when coords left is > ships left
      while (shots.size() < numShots) {
        int row = rand.nextInt(board.getHeight());
        int col = rand.nextInt(board.getWidth());
        Coord shot = new Coord(row, col);

        if (!shots.contains(shot) && !prevShots.contains(shot)) { // don't repeat shots
          shots.add(shot);
        }
      }
    } else { // when coords left is < ships left
      while (shots.size() < coordsLeft) {
        int row = rand.nextInt(board.getHeight());
        int col = rand.nextInt(board.getWidth());
        Coord shot = new Coord(row, col);

        if (!shots.contains(shot) && !prevShots.contains(shot)) { // don't repeat shots
          shots.add(shot);
        }
      }
    }
    prevShots.addAll(shots);
    return shots;
  }
}
