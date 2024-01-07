package cs3500.pa04;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ProxyDealer;
import cs3500.pa04.controller.SalvoGame;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.GameBoard;
import cs3500.pa04.model.Player;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Random aiRandom = new Random();
    GameBoard aiBoard = new GameBoard();
    Player aiPlayer = new AiPlayer(aiBoard, aiRandom);

    if (args.length == 0) { // salvo game
      try {
        Readable scan = new InputStreamReader(System.in);
        Appendable output = new PrintStream(System.out);
        Controller salvoGame = new SalvoGame(scan, output, aiPlayer, aiBoard);
        salvoGame.run();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    if (args.length != 0) { // proxy
      try {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Socket server = new Socket(host, port);
        Controller proxyDealer = new ProxyDealer(server, aiPlayer);
        proxyDealer.run();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
