package cs3500.pa03.controller;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.game.GameResult;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa03.model.player.User;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa03.viewer.TerminalView;
import cs3500.pa03.viewer.Viewer;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Controls the game for BattleSalvo
 */
public class TerminalController {
  private static final int HIGHEST = 15;
  private static final int LOWEST = 6;
  private final int[] fleetSize;
  private final Viewer viewer;
  private final PlayerExtend player;
  private final PlayerExtend robot;
  private int height;
  private int width;

  /**
   * Setups the controller
   *
   * @param appendable the appendable for output
   * @param readable   the readable for input
   * @param random the random seed
   */
  public TerminalController(Appendable appendable, Readable readable, Random random) {
    this.viewer = new TerminalView(appendable, readable);
    this.height = 0;
    this.width = 0;
    this.fleetSize = new int[4];
    this.player = createUser();
    this.robot = createRobot();
    player.setRandom(random);
    robot.setRandom(random);
  }

  /**
   * Checks if the text has an exit in it
   *
   * @param text the text to check
   * @throws IllegalArgumentException when text has exit in it
   */
  public static void checkExit(String text) {
    if ("EXIT".equalsIgnoreCase(text)) {
      throw new IllegalStateException("Game terminated.");
    }
  }

  /**
   * Runs the game
   */
  public void runGame() {
    try {
      setupGame();
      boolean shipsInWater = player.getFleetSize() > 0 && robot.getFleetSize() > 0;
      while (shipsInWater) {
        sendBoardData();
        attack();
        shipsInWater = player.getFleetSize() > 0 && robot.getFleetSize() > 0;
      }

      if (player.getFleetSize() > 0) {
        playerWins();
      } else if (robot.getFleetSize() > 0) {
        robotWins();
      } else {
        drawGame();
      }
    } catch (IOException e) {
      System.err.println("Error getting response.");
    } catch (IllegalStateException e) {
      System.err.println("Game terminated.");
    }
  }

  /**
   * Handles when the player wins
   *
   * @throws IOException when the text is unreadable
   */
  private void playerWins() throws IOException {
    player.endGame(GameResult.WIN, "Player destroyed all ships.");
    robot.endGame(GameResult.LOSE, "Player destroyed all ships.");
  }

  /**
   * Handles when the AI wins
   *
   * @throws IOException when the text is unreadable
   */
  private void robotWins() throws IOException {
    robot.endGame(GameResult.WIN, "AI destroyed all ships.");
    player.endGame(GameResult.LOSE, "AI destroyed all ships.");
  }

  /**
   * Handles when the game ends in a draw
   *
   * @throws IOException when the text is unreadable
   */
  private void drawGame() throws IOException {
    robot.endGame(GameResult.DRAW, "Player and AI destroyed all ships.");
    player.endGame(GameResult.DRAW, "Player and A destroyed all ships.");
  }

  /**
   * Handles the attack sequence
   *
   * @throws IOException when the text is unreadable
   */
  private void attack() throws IOException {
    List<Coord> playerShots = player.takeShots();
    List<Coord> robotReportShots = robot.reportDamage(playerShots);
    player.successfulHits(robotReportShots);
    List<Coord> robotShots = robot.takeShots();
    List<Coord> playerReportShots = player.reportDamage(robotShots);
    robot.successfulHits(playerReportShots);
  }

  /**
   * Handles the board data
   *
   * @throws IOException when the text is unreadable
   */
  private void sendBoardData() throws IOException {
    viewer.sendMessage("Opponent's Board\n");
    viewer.sendMessage(player.opponentToString());
    viewer.sendMessage("\nPlayer's Board\n");
    viewer.sendMessage(player.toString());
  }

  /**
   * Setups the game
   *
   * @throws IOException when the text is unreadable
   */
  private void setupGame() throws IOException {
    getDimensions();
    getFleetSize();
    Map<ShipType, Integer> specifications = setSpecifications();
    player.setup(height, width, specifications);
    robot.setup(height, width, specifications);
  }

  /**
   * Formats the messages to the desired format
   *
   * @returns a string of the formatted message
   */
  private String formatMessage(String message) {
    String formatMessage =
        message.trim() + "\n"
            + "------------------------------------------------------\n";
    return formatMessage;
  }

  /**
   * Gets the dimensions from the player
   *
   * @throws IOException when the text is unreadable
   */
  private void getDimensions() throws IOException {
    String welcome = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width below:";
    String invalidDimensions = "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width\n"
        + "of the game must be in the range (6, 15), inclusive.\n"
        + "For example, it could be: 10 10.\n"
        + "If you want to exit the program, respond with exit.\n"
        + "Please try again!\n";

    welcome = formatMessage(welcome);
    invalidDimensions = formatMessage(invalidDimensions);
    viewer.sendMessage(welcome);

    while (!validDimensions(height, width)) {
      String input = "";
      String[] values = new String[2];
      try {
        input = viewer.askUser("Respond here: ");
        checkExit(input);
        values = input.split(" ");
        height = Integer.parseInt(values[0]);
        width = Integer.parseInt(values[1]);
        if (!validDimensions(height, width)) {
          viewer.sendMessage(invalidDimensions);
        }
      } catch (Exception e) {
        checkExit(input);
        viewer.sendMessage(invalidDimensions);
        height = 0;
        width = 0;
      }
    }
  }

  /**
   * Sets the specifications
   */
  private Map<ShipType, Integer> setSpecifications() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    // ORDER: Carrier, Battleship, Destroyer, Submarine
    int carrier = 0;
    int battleship = 1;
    int destroyer = 2;
    for (int i = 0; i < fleetSize.length; i++) {
      if (i == carrier) {
        specifications.put(ShipType.CARRIER, fleetSize[i]);
      } else if (i == battleship) {
        specifications.put(ShipType.BATTLESHIP, fleetSize[i]);
      } else if (i == destroyer) {
        specifications.put(ShipType.DESTROYER, fleetSize[i]);
      } else {
        specifications.put(ShipType.SUBMARINE, fleetSize[i]);
      }
    }
    return specifications;
  }


  /**
   * Gets the fleet size
   *
   * @throws IOException when the text is unreadable
   */
  private void getFleetSize() throws IOException {
    String fleet = String.format(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size %d.", calculateFleetSize(height, width));
    String invalidFleet = "Uh Oh! You've entered invalid fleet sizes.\n" + fleet;

    fleet = formatMessage(fleet);
    invalidFleet = formatMessage(invalidFleet);
    viewer.sendMessage(fleet);

    String input = "";
    do {
      try {
        input = viewer.askUser("Respond here: ");
        checkExit(input);
        String[] values = input.split(" ");
        if (values.length != fleetSize.length) {
          throw new IllegalArgumentException("Invalid fleet size.");
        }
        for (int i = 0; i < fleetSize.length; i++) {
          fleetSize[i] = Integer.parseInt(values[i]);
        }
        if (!validFleetSize(fleetSize)) {
          viewer.sendMessage(invalidFleet); // viewer.sendMessage("fontenot was here");
        }
      } catch (Exception e) {
        checkExit(input);
        Arrays.fill(fleetSize, 0);
        viewer.sendMessage(invalidFleet);
      }
    } while (!validFleetSize(fleetSize));
  }

  /**
   * Calculates the fleet size max.
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @return the integer of the max fleet size
   */
  private int calculateFleetSize(int height, int width) {
    return Math.min(height, width);
  }

  /**
   * Checks if the fleet size is valid
   *
   * @param fleetSize the fleet size
   * @return true if the fleet size is valid, false otherwise.
   */
  private boolean validFleetSize(int[] fleetSize) {
    int totalSize = 0;
    for (int fleetNum : fleetSize) {
      if (fleetNum < 1) {
        return false;
      }
      totalSize += fleetNum;
    }
    return totalSize <= calculateFleetSize(height, width);
  }

  /**
   * Creates the user
   *
   * @return a new User object
   */
  private PlayerExtend createUser() {
    return new User("Player", viewer);
  }

  /**
   * Creates the AI
   *
   * @return a new AI object
   */
  private PlayerExtend createRobot() {
    return new ArtificialIntelligence("AI");
  }

  /**
   * Checks if the dimensions are valid
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @return true if the dimensions are valid, false otherwise.
   */
  private boolean validDimensions(int height, int width) {
    boolean validHeight = height <= HIGHEST && height >= LOWEST;
    boolean validWidth = width <= HIGHEST && width >= LOWEST;
    return validWidth && validHeight;
  }
}