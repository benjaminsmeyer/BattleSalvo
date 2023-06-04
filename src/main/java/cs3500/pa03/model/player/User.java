package cs3500.pa03.model.player;

import cs3500.pa03.controller.TerminalController;
import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.coords.Coords;
import cs3500.pa03.model.game.GameResult;
import cs3500.pa03.viewer.Viewer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * User class for Player
 */
public class User extends PlayerImpl {
  private static Viewer view;

  /**
   * Setups the player
   *
   * @param name the name of the player
   * @param view the viewer
   */
  public User(String name, Viewer view) {
    super(name);
    this.view = view;
    this.random = new Random();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   * @throws IOException when unable to take in data
   */
  @Override
  public List<Coord> takeShots() throws IOException {
    int amountAvailable = countAvailableSpots(opponentBoard);
    int shootAmount = Math.min(amountAvailable, fleetSize);
    String message = String.format("""

        Please Enter %d Shots (starts at 0 0, ends at %d %d):
        ---------------------------------------------
        ---------------------""", shootAmount, height, width);
    view.sendMessage(message);
    return getShotCoords();
  }

  /**
   * Gets the shots coords.
   * Starts at 0 0.
   *
   * @return the list of coords
   * @throws IOException when unable to get data
   */
  private List<Coord> getShotCoords() throws IOException {
    List<Coord> coords = new ArrayList<>();
    int count = 0;
    int amountAvailable = countAvailableSpots(opponentBoard);
    int shootAmount = Math.min(amountAvailable, fleetSize);
    while (count < shootAmount) {
      String message = String.format("Send coordinate (%d/%d):", count + 1, shootAmount);
      String input = view.askUser(message);
      String[] values = input.split(" ");
      int[] spot = new int[2];
      TerminalController.checkExit(input);
      try {
        if (values.length != spot.length) {
          throw new IllegalArgumentException("Invalid amount of shots.");
        }
        for (int i = 0; i < spot.length; i++) {
          spot[i] = Integer.parseInt(values[i]);
        }
        int x = spot[0];
        int y = spot[1];
        if (validShot(x, y)) {
          Coord coord = new Coords(x, y);
          coords.add(coord);
          opponentBoard[y][x] = MISS;
          count++;
        } else {
          view.sendMessage("Invalid shot. Choose a different coordinate.");
        }
      } catch (Exception e) {
        handleInvalidArg(input);
      }
    }
    return coords;
  }

  /**
   * Handles invalid shots arg.
   *
   * @param input the string
   * @throws IOException when it is unable to send data
   */
  private void handleInvalidArg(String input) throws IOException {
    TerminalController.checkExit(input);
    view.sendMessage(
        "Invalid arguments."
            + " It must be two numbers (e.g. 2 1"
            + ", where x is 2 and y is 1). Try again.");
  }

  /**
   * Checks if the shots are valid
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the shot is valid, false otherwise.
   */
  private boolean validShot(int x, int y) {
    try {
      return Objects.equals(opponentBoard[y][x], EMPTY);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Counts how many spots are available on board.
   *
   * @return the amount of available spots available on board
   */
  private int countAvailableSpots(String[][] board) {
    int count = 0;
    for (String[] row : board) {
      for (String str : row) {
        if (str.equalsIgnoreCase(EMPTY)) {
          count++;
        }
      }
    }
    return count;
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
    String message;
    if (result == GameResult.WIN) {
      message = "You won!";
    } else if (result == GameResult.DRAW) {
      message = "Game was a draw!";
    } else {
      message = "You lost!";
    }
    try {
      view.sendMessage(message + "\n" + reason);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
