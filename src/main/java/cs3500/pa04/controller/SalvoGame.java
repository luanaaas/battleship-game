package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameBoard;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualDataModel;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.UserPlayer;
import cs3500.pa04.view.GameView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the controller of the game
 */
public class SalvoGame implements Controller {

  private final Readable input;
  private final Appendable output;
  private final GameView view;
  private final ManualDataModel mdm;
  private final Player ai;
  private final Player user;
  private final GameBoard aisBoard;
  private final GameBoard usersBoard;
  private final Random rand;


  /**
   * @param input is the readable passed in from main
   * @param output is the output to the console
   * @param ai is the AI player in the game of BattleSalvo
   * @param aisBoard is the AI player's board
   */
  public SalvoGame(Readable input, Appendable output,
                   Player ai, GameBoard aisBoard) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.view = new GameView(input, output);
    this.mdm = new ManualDataModel(view);
    this.rand = new Random();
    this.aisBoard = aisBoard;
    this.usersBoard = new GameBoard();
    this.ai = ai;
    this.user = new UserPlayer(usersBoard, rand, mdm);
  }


  /**
   * Controls running the battle salvo game
   */
  @Override
  public void run() {
    try {
      int[] sizes = getBoardSize();
      int height = sizes[0];
      int width = sizes[1];
      Map<ShipType, Integer> fleetInfo = getFleet(sizes);
      user.setup(height, width, fleetInfo);
      ai.setup(height, width, fleetInfo);
      view.displayBoards(aisBoard.getBoardCoord(), usersBoard.getBoardCoord());
      List<Coord> allUserShots = new ArrayList<>();
      List<Coord> allAiShots = new ArrayList<>();
      List<Coord> allUsersDamage = new ArrayList<>();
      List<Coord> allAisDamage = new ArrayList<>();
      boolean gameOver = false;
      while (!gameOver) {
        view.prompts("\nPlease Enter: " + usersBoard.shipsLeft() + " shots");
        List<Coord> userShots = getValidShots(user.takeShots());
        List<Coord> aiShots = ai.takeShots();
        allUserShots.addAll(userShots);
        allAiShots.addAll(aiShots);
        List<Coord> aiBoardDamage = ai.reportDamage(userShots);
        List<Coord> userBoardDamage = user.reportDamage(aiShots);
        allUsersDamage.addAll(userBoardDamage);
        allAisDamage.addAll(aiBoardDamage);
        user.successfulHits(aiBoardDamage);
        ai.successfulHits(userBoardDamage);
        view.prompts("\nShots fired by the user which hit AI ships: " + allAisDamage.size() + "\n");
        view.prompts("Shots fired by the user which did not hit ships: "
            + (allUserShots.size() - allAisDamage.size()) + "\n");
        view.prompts("Shots fired by the AI which hit user ships: " + allUsersDamage.size() + "\n");
        view.prompts("Shots fired by the AI which did not hit user ships: "
            + (allAiShots.size() - allUsersDamage.size()) + "\n");
        gameOver = checkForGameOver();
      }
      endStats(usersBoard, aisBoard);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  /**
   * @return true if the game is over and false if it isn't, and displaying the state of the game
   */
  private boolean checkForGameOver() {
    if (usersBoard.isGameOver() || aisBoard.isGameOver()) {
      return true;
    }
    try {
      view.displayBoards(aisBoard.getBoardCoord(), usersBoard.getBoardCoord());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  /**
   * Verifies user's board size input to be within bounds
   *
   * @return valid board size input
   */
  private int[] getBoardSize() throws IOException {
    view.prompts("Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width: ");
    int[] sizes = view.promptGridSize();
    int height = sizes[0];
    int width = sizes[1];

    if (height < 6 || height > 15 || width < 6 || width > 15) {
      view.prompts("Uh Oh! You've entered invalid dimensions.\n"
          + "Please remember that the height and width of the game\n"
          + "must be in the range (6, 15), inclusive. Try again!");
      sizes = view.promptGridSize();
    }
    return sizes;
  }

  /**
   * Verifies user's fleet size input to be within bounds
   *
   * @param sizes fleet sizes inputted by user
   * @return the ship types and their amounts chosen
   */
  private Map<ShipType, Integer> getFleet(int[] sizes) throws IOException {
    Arrays.sort(sizes);
    int maxFleetSize = sizes[0];
    view.prompts("Please enter your fleet in the order"
        + "[Carrier, Battleship, Destroyer, Submarine]\n"
        + "Remember, your fleet may not exceed size " + maxFleetSize + ".");
    int[] shipAmounts = view.promptFleetSize();
    int carrierCount = shipAmounts[0];
    int battleshipCount = shipAmounts[1];
    int destroyerCount = shipAmounts[2];
    int submarineCount = shipAmounts[3];
    int totalShipCount = carrierCount + battleshipCount + destroyerCount + submarineCount;

    if (totalShipCount > maxFleetSize) {
      output.append("Uh Oh! You've entered invalid fleet sizes. \n"
          + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]. \n"
          + "Remember, your fleet may not exceed size " + maxFleetSize + ".\n");
      shipAmounts = view.promptFleetSize();  // retry entering fleet size
    }
    carrierCount = shipAmounts[0];
    battleshipCount = shipAmounts[1];
    destroyerCount = shipAmounts[2];
    submarineCount = shipAmounts[3];
    Map<ShipType, Integer> fleetSizes = new HashMap<>();
    fleetSizes.put(ShipType.CARRIER, carrierCount);
    fleetSizes.put(ShipType.BATTLESHIP, battleshipCount);
    fleetSizes.put(ShipType.DESTROYER, destroyerCount);
    fleetSizes.put(ShipType.SUBMARINE, submarineCount);
    return fleetSizes;
  }

  /**
   * Verifies user's shot coordinates to be within grid size
   *
   * @param shots user's inputted shots
   * @return valid user shots
   */
  private List<Coord> getValidShots(List<Coord> shots) throws IOException {
    int height = usersBoard.getHeight();
    int width = usersBoard.getWidth();

    for (Coord c : shots) {
      int x = c.getX();
      int y = c.getY();
      if (x >= width || y >= height) {
        output.append("Shots out of board bounds, try again!\n");
        return getValidShots(user.takeShots());
      }
    }
    return shots;
  }

  /**
   * Displays all ending statistics together
   *
   * @param userBoard the manual player's board
   * @param aiBoard   the opponents board
   */
  private void endStats(GameBoard userBoard, GameBoard aiBoard) throws IOException {
    if (userBoard.isGameOver() && !aiBoard.isGameOver()) {
      view.showEndStats(GameResult.LOSE, "all your ships we're sunk.\n");
    } else if (aiBoard.isGameOver() && !userBoard.isGameOver()) {
      view.showEndStats(GameResult.WIN, "you sunk all your opponents ships!\n");
    } else if (userBoard.isGameOver() && aiBoard.isGameOver()) {
      view.showEndStats(GameResult.DRAW, "both you and your opponent sunk each"
          + "other's ships at the same time.\n");
    }
  }
}
