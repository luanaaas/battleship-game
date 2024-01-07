package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the viewing and scanning of the game
 */
public class GameView {

  private Appendable output;
  private Readable input;
  private Scanner sc;

  /**
   * Constructor for GameView
   *
   * @param input the users input
   * @param output output after user input to console
   */
  public GameView(Readable input, Appendable output) {
    this.input = input;
    this.sc = new Scanner(input);
    this.output = output;
  }


  /**
   * Shows given message
   *
   * @param prompt desired prompt to be shown to user
   * @throws IOException if not proper output appended
   */
  public void prompts(String prompt) throws IOException {
    output.append(prompt + "\n");
  }

  /**
   * Scans user input of grid sizes
   *
   * @return grid sizes chosen
   */
  public int[] promptGridSize() {
    int[] sizes = new int[2];
    if (sc.hasNextInt()) {
      int height;
      int width;
      height = sc.nextInt();
      width = sc.nextInt();
      sizes = new int[] {height, width};
    }
    return sizes;
  }

  /**
   * Scans user input of fleet sizes
   *
   * @return fleet sizes chosen
   */
  public int[] promptFleetSize() {
    int[] count = new int[4];
    if (sc.hasNextInt()) {
      int carrierCount;
      int battleshipCount;
      int destroyerCount;
      carrierCount = sc.nextInt();
      battleshipCount = sc.nextInt();
      destroyerCount = sc.nextInt();
      int submarineCount;
      submarineCount = sc.nextInt();
      count = new int[] {carrierCount, battleshipCount, destroyerCount, submarineCount};
    }
    return count;
  }

  /**
   * Displays both boards of players
   *
   * @param aiBoard opponents board
   * @param userBoard the user's or manual player's board
   * @throws IOException if not proper output appended
   */
  public void displayBoards(Coord[][] aiBoard, Coord[][] userBoard) throws IOException {
    output.append("\nOpponent Board Data: \n");
    displayOpponentBoard(aiBoard);
    output.append("\nYour Board: \n");
    displayUserBoard(userBoard);
  }

  /**
   * Displays the user's board
   *
   * @param userBoard the user's or manual player's board
   * @throws IOException if not proper output appended
   */
  private void displayUserBoard(Coord[][] userBoard) throws IOException {
    for (int i = 0; i < userBoard.length; i++) {
      for (int j = 0; j < userBoard[i].length; j++) {
        Coord c = userBoard[i][j];
        char look = c != null ? c.getUserLook() : '0';
        output.append(look + " ");
      }
      output.append("\n");
    }
  }

  /**
   * Displays the opponent's board
   *
   * @param aiBoard the opponent's board
   * @throws IOException if not proper output appended
   */
  private void displayOpponentBoard(Coord[][] aiBoard) throws IOException {
    for (int i = 0; i < aiBoard.length; i++) {
      for (int j = 0; j < aiBoard[i].length; j++) {
        Coord c = aiBoard[i][j];
        char look = c != null ? c.getOpponentLook() : '0';
        output.append(look + " ");
      }
      output.append("\n");
    }
  }


  /**
   * Scans user input for shots places
   *
   * @param shotsAllowed the amount of shots allowed based on ships left
   * @return the list shots and a list of coordinates
   * @throws IOException if not proper output appended
   */
  public List<Coord> promptShots(int shotsAllowed) throws IOException {
    output.append("-------------------------------------------\n");
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < shotsAllowed; i++) {
      if (sc.hasNextInt()) {
        int x;
        int y;
        x = sc.nextInt();
        y = sc.nextInt();
        sc.nextLine();
        Coord coord = new Coord(x, y);
        shots.add(coord);
      }
    }
    return shots;
  }

  /**
   * Shows the ending game statistics
   *
   * @param result one of three potential game ending results
   * @param reason why the game ended
   * @throws IOException if not proper output appended
   */
  public void showEndStats(GameResult result, String reason) throws IOException {
    if (result == GameResult.WIN) {
      output.append("\nYou won! ");
    } else if (result == GameResult.LOSE) {
      output.append("\nYou lost. ");
    } else if (result == GameResult.DRAW) {
      output.append("\nThere was a draw! ");
    }
    output.append("The reason was " + reason);
  }

}
