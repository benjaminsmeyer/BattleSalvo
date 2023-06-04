package cs3500.pa03;

import cs3500.pa03.controller.GameController;
import java.io.InputStreamReader;
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
    Appendable appendable = System.out;
    Readable input = new InputStreamReader(System.in);
    GameController controller = new GameController(appendable, input, new Random());
    controller.runGame();
  }
}