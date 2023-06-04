package cs3500.pa03.model.player;

import java.util.Random;

/**
 * Extends player interface to handle getters and toStrings
 */
public interface PlayerExtend extends Player {
  /**
   * Get the user's board.
   *
   * @return the user's board
   */
  String[][] getBoard();

  /**
   * Get the user's opponent board.
   *
   * @return the user's board
   */
  String[][] getOpponentBoard();

  /**
   * Get the current fleet size.
   *
   * @return the current fleet size
   */
  int getFleetSize();

  /**
   * Gets the player's board.
   *
   * @return the player's board.
   */
  String toString();

  /**
   * Gets the player's current find of the opponent's board.
   *
   * @return the opponent's board.
   */
  String opponentToString();

  /**
   * Sets the random seed for the game.
   *
   * @param random the random seed for the game
   */
  void setRandom(Random random);
}
