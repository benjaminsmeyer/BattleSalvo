package cs3500.pa03.model.player;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.game.BoardGen;
import cs3500.pa03.model.game.GameResult;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Abstract player class that implements player
 */
public abstract class PlayerImpl implements PlayerExtend {
  static final String HIT = "H";
  static final String MISS = "M";
  static final String EMPTY = "0";
  private static String name;
  int fleetSize;
  String[][] board;
  String[][] opponentBoard;
  Map<String, Coord> coords;
  Random random;
  GameResult result;
  int width;
  int height;

  /**
   * Setups the player
   *
   * @param name the name of the player
   */
  public PlayerImpl(String name) {
    this.name = name;
    this.random = new Random();
    this.board = new String[0][];
    this.opponentBoard = new String[0][];
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    setupBoard(height, width, specifications);
    this.height = height;
    this.width = width;
    Set<Ship> ships = new HashSet<>();
    for (String current : coords.keySet()) {
      Coord currentCoords = coords.get(current);
      if (currentCoords.checkSpotInUse()) {
        ships.add(currentCoords.spotTaken());
      }
    }
    return ships.stream().toList();
  }

  /**
   * Setups the game board.
   *
   * @param height         the height of the game board
   * @param width          the width of the game board
   * @param specifications the specifications of the game
   */
  private void setupBoard(int height, int width, Map<ShipType, Integer> specifications) {
    BoardGen boardGen = new BoardGen(height, width, specifications, random);
    boardGen.createBoard();
    board = boardGen.getBoard();
    coords = boardGen.getCoords();
    fleetSize = boardGen.countFleetSize(specifications);
    opponentBoard = boardGen.createEmptyBoard();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   * @throws IOException when unable to take in data
   */
  @Override
  public abstract List<Coord> takeShots() throws IOException;

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coords = new ArrayList<>();
    for (Coord current : opponentShotsOnBoard) {
      current = this.coords.get(current.toString());
      if (current.checkSpotInUse()) {
        current.spotHit();
        coords.add(current);
        if (!current.spotTaken().shipInWater()) {
          fleetSize--;
        }
        board[current.getAxisY()][current.getAxisX()] = HIT;
      } else {
        board[current.getAxisY()][current.getAxisX()] = MISS;
      }
    }
    return coords;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      opponentBoard[coord.getAxisY()][coord.getAxisX()] = HIT;
    }
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
    this.result = result;
  }

  /**
   * Get the user's board.
   *
   * @return the user's board
   */
  public String[][] getBoard() {
    return board;
  }

  /**
   * Get the user's opponent board.
   *
   * @return the user's board
   */
  public String[][] getOpponentBoard() {
    return opponentBoard;
  }

  /**
   * Get the current fleet size.
   *
   * @return the current fleet size
   */
  public int getFleetSize() {
    return fleetSize;
  }

  /**
   * Gets the player's board.
   *
   * @return the player's board.
   */
  @Override
  public String toString() {
    return boardToString(board);
  }

  /**
   * Gets the player's current find of the opponent's board.
   *
   * @return the opponent's board.
   */
  public String opponentToString() {
    return boardToString(opponentBoard);
  }

  /**
   * Sets the random seed for the game.
   *
   * @param random the random seed for the game
   */
  @Override
  public void setRandom(Random random) {
    this.random = random;
  }

  /**
   * Converts the board to a string
   *
   * @param board the board
   * @return a string of the board
   */
  private String boardToString(String[][] board) {
    StringBuilder sb = new StringBuilder();
    for (String[] row : board) {
      for (int i = 0; i < row.length; i++) {
        sb.append(row[i]);
        String space = i < row.length - 1 ? " " : "";
        sb.append(space);
      }
      sb.append("\n");
    }
    return sb.toString().trim();
  }
}
