package cs3500.pa04;

import cs3500.pa03.controller.TerminalController;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa04.client.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

/**
 * The Driver is responsible for connecting to the server
 * and then running an entire game with a player.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, String port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, Integer.parseInt(port));

    PlayerExtend player = new ArtificialIntelligence("benjaminsmeyer");
    ProxyController proxyController = new ProxyController(server, player);
    proxyController.run();
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   * No parameters run the offline terminal experience.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      runTerminalGame();
    } else {
      runOnlineGame(args);
    }
  }

  /**
   * Runs the terminal game
   */
  private static void runTerminalGame() {
    Appendable appendable = System.out;
    Readable input = new InputStreamReader(System.in);
    TerminalController controller = new TerminalController(appendable, input, new Random());
    controller.runGame();
  }

  /**
   * Runs the online game.
   *
   * @param args The expected parameters are the server's host and port
   */
  private static void runOnlineGame(String[] args) {
    try {
      if (args.length == 2) {
        runClient(args[0], args[1]);
      } else {
        System.out.println("Expected two arguments: `[host] [port]`.");
      }
    } catch (IOException | IllegalStateException e) {
      System.out.println("Unable to connect to the server.");
    } catch (NumberFormatException e) {
      System.out.println("Second argument should be an integer. Format: `[host] [port]`.");
    }
  }
}