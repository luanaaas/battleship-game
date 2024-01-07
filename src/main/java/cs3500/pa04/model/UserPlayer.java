package cs3500.pa04.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a manual user player in game
 */
public class UserPlayer extends Aplayer {

  private ManualDataModel mdm;

  /**
   * Represents constructor for a user player
   *
   * @param board this users game board
   * @param  rand the random for this user
   * @param mdm a manual data model for the users shots
   */
  public UserPlayer(GameBoard board, Random rand, ManualDataModel mdm) {
    super(board, rand);
    this.mdm = mdm;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "User";
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
    try {
      shots = mdm.shots(board.shipsLeft());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return shots;
  }
}
